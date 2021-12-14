package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcMultiplicityType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.DragPointViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Visual representation of the arc.
 */
public abstract class ArcView extends GraphElementView {

    public static int LINE_WIDTH = 1;

    private Group container;
    private Group groupLines;
    private final List<DragPointView> dragPointViews;
    private final ConnectableGraphElementView fromElementView;
    private final ConnectableGraphElementView toElementView;
    protected Group groupSymbols;
    protected List<Line> lines;
    protected ArcEnding ending;
    private final ChangeListener<Boolean> onIsFlushingChangedListener;
    private DragPointView lastAddedDragPointView;
    private ArcMultiplicityText multiplicityText;

    private final ListChangeListener<? super DragPointViewModel> onDragPointsChangedListener;
    private final ChangeListener<String> onMultiplicityChangedListener;
    private final ChangeListener<? super ArcMultiplicityType> onMultiplicityTypeChangedListener;
    private ArcMultiplicityFlushing multiplicityFlushing;

    public ArcView(GraphView graphView, ArcViewModel arcViewModel, ConnectableGraphElementView from, ConnectableGraphElementView to) {
        super(graphView, arcViewModel);

        lines = new ArrayList<>();
        dragPointViews = new ArrayList<>();
        this.fromElementView = from;
        this.toElementView = to;
        this.onDragPointsChangedListener = this::onDragPointsChangedListener;
        this.onMultiplicityChangedListener = this::onMultiplicityChangedListener;
        this.onMultiplicityTypeChangedListener = this::onMultiplicityTypeChangedListener;
        this.onIsFlushingChangedListener = this::onIsFlushingChangedListener;

        createView(from, to);
    }

    private void onIsFlushingChangedListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            multiplicityFlushing.setVisible(true);
            multiplicityText.setVisible(false);
        } else {
            multiplicityFlushing.setVisible(false);
            setMultiplicityTextVisibility();
        }
    }

    private void onMultiplicityChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        setMultiplicityTextVisibility();
    }

    private void onMultiplicityTypeChangedListener(ObservableValue<? extends ArcMultiplicityType> observableValue, ArcMultiplicityType oldValue, ArcMultiplicityType newValue) {
        setMultiplicityTextVisibility();
    }

    private void setMultiplicityTextVisibility() {
        multiplicityText.setVisible(!getViewModel().getMultiplicity().equals("1") && getViewModel().getMultiplicityType() == ArcMultiplicityType.Constant);
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
        multiplicityText = new ArcMultiplicityText();
        multiplicityFlushing = new ArcMultiplicityFlushing();

        groupLines = new Group();
        groupSymbols = new Group(multiplicityText.getRoot(), multiplicityFlushing.getRoot());
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
        multiplicityText.update(line);
        multiplicityFlushing.update(line);
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
            lastAddedDragPointView.onMousePressedHandler(mouseEvent);
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

    @Override
    protected void bindViewModel() {
        multiplicityText.bindViewModel(getViewModel());

        createDragPoints(getViewModel().getDragPoints());
        getViewModel().getDragPoints().addListener(this.onDragPointsChangedListener);
        getViewModel().multiplicityProperty().addListener(this.onMultiplicityChangedListener);
        getViewModel().multiplicityTypeProperty().addListener(this.onMultiplicityTypeChangedListener);
        getViewModel().isFlushingProperty().addListener(this.onIsFlushingChangedListener);
        getViewModel().setRemoveStraightLinesCallback(() -> {
            this.removeStraightConnections();
            return null;
        });

        onMultiplicityChangedListener(null, null, getViewModel().getMultiplicity());
        onMultiplicityTypeChangedListener(null, null, getViewModel().getMultiplicityType());
        onIsFlushingChangedListener(null, null, getViewModel().isFlushing());

        super.bindViewModel();
    }

    @Override
    public void unbindViewModel() {
        multiplicityText.unbindViewModel();

        getViewModel().getDragPoints().removeListener(this.onDragPointsChangedListener);
        getViewModel().multiplicityProperty().removeListener(this.onMultiplicityChangedListener);
        getViewModel().multiplicityTypeProperty().removeListener(this.onMultiplicityTypeChangedListener);
        getViewModel().isFlushingProperty().removeListener(this.onIsFlushingChangedListener);
        getViewModel().setRemoveStraightLinesCallback(null);

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
        groupLines.getChildren().add(line);

        sourceLine.setEndX(dragPointViewModel.getPositionX());
        sourceLine.setEndY(dragPointViewModel.getPositionY());

        lastAddedDragPointView = new DragPointView(graphView, this, dragPointViewModel);
        if (getViewModel().isHighlighted()) {
            lastAddedDragPointView.getViewModel().highlightedProperty().set(true);
        }

        groupSymbols.getChildren().add(lastAddedDragPointView.getMiddleLayerContainer());
        lastAddedDragPointView.addedToParent();
        lastAddedDragPointView.setGraphView(graphView);
        dragPointViews.add(index, lastAddedDragPointView);
    }

    public void onDragPointMovedHandler(DragPointView dragPointView) {
        int index = dragPointViews.indexOf(dragPointView);
        Line lineTo = lines.get(index);
        var center = dragPointView.getCenterPosition();
        lineTo.setEndX(center.getX());
        lineTo.setEndY(center.getY());
        Line lineFrom = lines.get(index + 1);
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
    protected void enableHighlight() {
        lines.forEach(line -> line.setEffect(highlightEffect));
        dragPointViews.forEach(dragPointView -> dragPointView.getViewModel().highlightedProperty().set(true));
        ending.getShape().setEffect(highlightEffect);
    }

    @Override
    protected void disableHighlight() {
        lines.forEach(line -> line.setEffect(null));
        dragPointViews.forEach(dragPointView -> dragPointView.getViewModel().highlightedProperty().set(false));
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
//        dragPointViews.forEach(dragPointView -> {
//            dragPointView.removedFromParent();
//            dragPointView.unbindViewModel();
//            dragPointView.setGraphView(null);
//        });
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
        return getViewModel().getFromViewModel().isHighlighted() && getViewModel().getToViewModel().isHighlighted();
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

    public List<Point2D> getAllPoints() {
        var lineSegments = new ArrayList<Point2D>();
        lineSegments.add(fromElementView.getShapeCenter());
        lineSegments.addAll(dragPointViews.stream().map(DragPointView::getShapeCenter).collect(Collectors.toList()));
        lineSegments.add(toElementView.getShapeCenter());
        return lineSegments;
    }

}
