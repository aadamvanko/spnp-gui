package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DragPointViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class ArcView extends GraphElementView {

    public static int LINE_WIDTH = 4;

    private Group container;
    private Group groupLines;
    private final List<DragPointView> dragPointViews;
    private final ConnectableGraphElementView fromElementView;
    private final ConnectableGraphElementView toElementView;
    protected Group groupSymbols;
    protected List<Line> lines;
    protected ArcEnding ending;
    private final ListChangeListener<? super DragPointViewModel> onDragPointsChangedListener;
    private Text textMultiplicity;
    private DragPointView lastAddedDragPointView;

    public ArcView(GraphView graphView, ArcViewModel arcViewModel, ConnectableGraphElementView from, ConnectableGraphElementView to) {
        super(graphView, arcViewModel);

        lines = new ArrayList<>();
        dragPointViews = new ArrayList<>();
        this.fromElementView = from;
        this.toElementView = to;
        this.onDragPointsChangedListener = this::onDragPointsChangedListener;

        createView(from, to);
        bindViewModel();
    }

    public ArcViewModel getViewModel() {
        return (ArcViewModel) viewModel;
    }

    private void onDragPointsChangedListener(ListChangeListener.Change<? extends DragPointViewModel> dragPointsChange) {
        while (dragPointsChange.next()) {
            for (var removedViewModel : dragPointsChange.getRemoved()) {
                var dragPointView = dragPointViews.stream().filter(dpv -> dpv.getViewModel() == removedViewModel).findAny().get();
                destroyDragPointView(dragPointView);
            }

            for (var addedViewModel : dragPointsChange.getAddedSubList()) {
                createDragPointView(addedViewModel);
            }
        }
    }

    private void createView(ConnectableGraphElementView from, ConnectableGraphElementView to) {
        textMultiplicity = new Text();
        textMultiplicity.textProperty().addListener((observableValue, oldValue, newValue) -> {
            textMultiplicity.setVisible(!newValue.equals("1"));
        });

        groupLines = new Group();
        groupSymbols = new Group(textMultiplicity);
        container = new Group(groupLines, groupSymbols);

        createFirstLine();
    }

    private void createFirstLine() {
        fromElementView.addArc(this);
        toElementView.addArc(this);

        Line line = createLine(fromElementView.getShapeCenter(), toElementView.getShapeCenter());
        lines.add(line);
        groupLines.getChildren().add(line);

        updateMultiplicityPosition();
    }

    private void updateMultiplicityPosition() {
        Line line = lines.get(lines.size() / 2);
        var start = new Point2D(line.getStartX(), line.getStartY());
        var end = new Point2D(line.getEndX(), line.getEndY());
        var midpoint = start.midpoint(end);
        var vector = end.subtract(start).normalize();
        var perpendicular = new Point2D(vector.getY(), -vector.getX());
        double OFFSET = 10;
        var pos = midpoint.add(perpendicular.multiply(OFFSET));
        textMultiplicity.setX(pos.getX());
        textMultiplicity.setY(pos.getY());
        textMultiplicity.setX(textMultiplicity.getX() - textMultiplicity.getLayoutBounds().getWidth() / 2);
        textMultiplicity.setY(textMultiplicity.getY() + textMultiplicity.getLayoutBounds().getHeight() / 4);
    }

    public List<DragPointView> getDragPointViews() {
        return dragPointViews;
    }

    public Node getRoot() {
        return container;
    }

    private Line createLine(double startX, double startY, double endX, double endY) {
        return createLine(new Point2D(startX, startY), new Point2D(endX, endY));
    }

    private Line createLine(Point2D start, Point2D end) {
        Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
//        System.out.println(line);
        line.setStrokeWidth(LINE_WIDTH);
        registerMouseHandlers(line);
        line.setSmooth(true);

        return line;
    }

    @Override
    public void onMousePressedHandler(MouseEvent mouseEvent) {
        super.onMousePressedHandler(mouseEvent);

        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Point2D mousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            Line sourceLine = (Line) mouseEvent.getSource();
            getViewModel().getDragPoints().add(lines.indexOf(sourceLine), new DragPointViewModel(mouseEvent.getX(), mouseEvent.getY()));
            System.out.println("last added drag point view " + lastAddedDragPointView);
            lastAddedDragPointView.onMousePressedHandler(mouseEvent);
//            savedMouseEvent = mouseEvent;
        }
    }

    @Override
    public void onMouseDraggedHandler(MouseEvent mouseEvent) {
        super.onMouseDraggedHandler(mouseEvent);

        if (lastAddedDragPointView != null) {
            lastAddedDragPointView.onMouseDraggedHandler(mouseEvent);
        }
    }

    @Override
    public void onMouseReleasedHandler(MouseEvent mouseEvent) {
        super.onMouseReleasedHandler(mouseEvent);

        if (lastAddedDragPointView != null) {
            lastAddedDragPointView.onMouseReleasedHandler(mouseEvent);
            lastAddedDragPointView = null;
        }
    }

    private void bindViewModel() {
        textMultiplicity.textProperty().bind(getViewModel().multiplicityProperty());

        createDragPoints(getViewModel().getDragPoints());

        getViewModel().getDragPoints().addListener(this.onDragPointsChangedListener);
    }

    @Override
    public void unbindViewModel() {
        textMultiplicity.textProperty().unbind();

        getViewModel().getDragPoints().removeListener(this.onDragPointsChangedListener);

        super.unbindViewModel();
    }

    private void createDragPoints(ObservableList<DragPointViewModel> dragPointViewModels) {
        for (var dragPointViewModel : dragPointViewModels) {
            createDragPointView(dragPointViewModel);
        }
    }

    private void createDragPointView(DragPointViewModel dragPointViewModel) {
        int dragPointViewModelIndex = getViewModel().getDragPoints().indexOf(dragPointViewModel);
        var sourceLine = lines.get(dragPointViewModelIndex);
        int index = lines.indexOf(sourceLine);

        Line line = createLine(dragPointViewModel.getPositionX(), dragPointViewModel.getPositionY(), sourceLine.getEndX(), sourceLine.getEndY());
        lines.add(index + 1, line);
//        System.out.println(lines);
        groupLines.getChildren().add(line);

        sourceLine.setEndX(dragPointViewModel.getPositionX());
        sourceLine.setEndY(dragPointViewModel.getPositionY());
//        System.out.println(lines);

        lastAddedDragPointView = new DragPointView(graphView, this, dragPointViewModel);
        if (isHighlighted()) {
            lastAddedDragPointView.enableHighlight();
        }

        System.out.println("adding drag point");
        groupSymbols.getChildren().add(lastAddedDragPointView.getMiddleLayerContainer());
        lastAddedDragPointView.addedToParent();
        lastAddedDragPointView.setGraphView(graphView);
        System.out.println("arc drag point graph view " + graphView);
        dragPointViews.add(index, lastAddedDragPointView);
    }

    public void onDragPointMovedHandler(DragPointView dragPointView) {
        int index = dragPointViews.indexOf(dragPointView);
//        System.out.println("drag point index " + index);
        Line lineTo = lines.get(index);
//        System.out.println("lineTo " + lineTo);
        var center = dragPointView.getCenterPosition();
        lineTo.setEndX(center.getX());
        lineTo.setEndY(center.getY());
        Line lineFrom = lines.get(index + 1);
//        System.out.println("lineFrom " + lineFrom);
        lineFrom.setStartX(center.getX());
        lineFrom.setStartY(center.getY());

        if (index == 0) {
            setStart(fromElementView.getBorderConnectionPoint(getLineEnd(lines.get(0))));
        }

        if (index == dragPointViews.size() - 1) {
            setEnd(toElementView.getBorderConnectionPoint(getLineStart(lines.get(index + 1))));
        }

        updateMultiplicityPosition();
    }

    private Point2D getLineEnd(Line line) {
        return new Point2D(line.getEndX(), line.getEndY());
    }

    private Point2D getLineStart(Line line) {
        return new Point2D(line.getStartX(), line.getStartY());
    }

    public void setStart(double x, double y) {
        Line firstLine = lines.get(0);
        firstLine.setStartX(x);
        firstLine.setStartY(y);
    }

    public void setStart(Point2D point) {
        setStart(point.getX(), point.getY());
    }

    public void setEnd(double x, double y) {
        Line lastLine = lines.get(lines.size() - 1);
        lastLine.setEndX(x);
        lastLine.setEndY(y);
        ending.update(lastLine);
        moveLastLineEndBack();
    }

    private void moveLastLineEndBack() {
        Line lastLine = lines.get(lines.size() - 1);
        var start = new Point2D(lastLine.getStartX(), lastLine.getStartY());
        var end = new Point2D(lastLine.getEndX(), lastLine.getEndY());
        var direction = end.subtract(start);
        var newEnd = end.subtract(direction.normalize().multiply(ArcEndingArrow.LENGTH * 0.75));
        lastLine.setEndX(newEnd.getX());
        lastLine.setEndY(newEnd.getY());
    }

    public void setEnd(Point2D point) {
        setEnd(point.getX(), point.getY());
    }

    public void updateEnds(ConnectableGraphElementView source) {
        setStart(fromElementView.getBorderConnectionPoint(getLineEnd(lines.get(0))));
        setEnd(toElementView.getBorderConnectionPoint(getLineStart(lines.get(lines.size() - 1))));
        updateMultiplicityPosition();
    }

    @Override
    public void enableHighlight() {
        super.enableHighlight();

        lines.forEach(line -> line.setEffect(highlightEffect));
        dragPointViews.forEach(dragPointView -> dragPointView.enableHighlight());
        ending.getShape().setEffect(highlightEffect);
    }

    @Override
    public void disableHighlight() {
        super.disableHighlight();

        lines.forEach(line -> line.setEffect(null));
        dragPointViews.forEach(dragPointView -> dragPointView.disableHighlight());
        ending.getShape().setEffect(null);
    }

    @Override
    public Node getBottomLayerContainer() {
        return groupLines;
    }

    @Override
    public Node getMiddleLayerContainer() {
        return groupSymbols;
    }

    @Override
    public Node getTopLayerContainer() {
        return null;
    }

    @Override
    public void addedToParent() {
    }

    @Override
    public void removedFromParent() {
        fromElementView.removeArc(this);
        toElementView.removeArc(this);
        getViewModel().getDragPoints().clear();
    }

    @Override
    public void snapToGrid() {
        dragPointViews.forEach(dragPointView -> dragPointView.snapToGrid());
    }

    @Override
    public void move(Point2D moveOffset) {
    }

    @Override
    public boolean canMove(Point2D offset) {
        return true;
    }

    public boolean hasHighlightedEnds() {
        return fromElementView.isHighlighted() && toElementView.isHighlighted();
    }

    public void removeStraightConnections() {
        for (int i = 0; i < dragPointViews.size(); ) {
            Line lineTo = lines.get(i);
            Line lineFrom = lines.get(i + 1);
            if (areInLine(getLineStart(lineTo), getLineEnd(lineTo), getLineEnd(lineFrom))) {
                getViewModel().getDragPoints().remove(dragPointViews.get(i).getViewModel());
            } else {
                i++;
            }
        }
    }

    private boolean areInLine(Point2D start, Point2D between, Point2D end) {
        int startX = (int) start.getX();
        int startY = (int) start.getY();
        int betweenX = (int) between.getX();
        int betweenY = (int) between.getY();
        int endX = (int) end.getX();
        int endY = (int) end.getY();
        return crossScalar2D(startX, startY, betweenX, betweenY, betweenX, betweenY, endX, endY) == 0;
    }

    private int crossScalar2D(int aX, int aY, int bX, int bY, int cX, int cY, int dX, int dY) {
        int abX = aX - bX;
        int abY = aY - bY;
        int cdX = cX - dX;
        int cdY = cY - dY;
//        System.out.println(abX * cdY - cdX * abY);
        return abX * cdY - cdX * abY;
    }

    public void destroyDragPointView(DragPointView dragPointView) {
        groupSymbols.getChildren().remove(dragPointView.getMiddleLayerContainer());
        dragPointView.removedFromParent();
        dragPointView.setGraphView(null);
        dragPointView.unbindViewModel();

        int index = dragPointViews.indexOf(dragPointView);
        dragPointViews.remove(index);

        Line lineTo = lines.get(index);
        Line lineFrom = lines.get(index + 1);
        lineTo.setEndX(lineFrom.getEndX());
        lineTo.setEndY(lineFrom.getEndY());
        lines.remove(lineFrom);
        groupLines.getChildren().remove(lineFrom);

        updateMultiplicityPosition();
        updateEnds(toElementView);
    }

    @Override
    public Node getContextMenuNode() {
        return lines.get(0);
    }

    public Group getGroupSymbols() {
        return groupSymbols;
    }
}
