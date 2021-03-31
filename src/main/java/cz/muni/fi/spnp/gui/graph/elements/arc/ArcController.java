package cz.muni.fi.spnp.gui.graph.elements.arc;

import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.graph.elements.GraphElement;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public abstract class ArcController extends GraphElement {

    public static int LINE_WIDTH = 2;

    private final Group container;
    private final Group groupLines;
    private final List<ArcDragMark> dragMarks;
    private final ConnectableGraphElement fromElement;
    private final ConnectableGraphElement toElement;
    protected Group groupSymbols;
    protected List<Line> lines;
    protected ArcEnding ending;
    private ArcDragMark lastAddedDragMark;

    public ArcController(ConnectableGraphElement from, ConnectableGraphElement to) {
        lines = new ArrayList<>();
        dragMarks = new ArrayList<>();
        this.fromElement = from;
        this.toElement = to;

        groupLines = new Group();
        groupSymbols = new Group();
        container = new Group(groupLines, groupSymbols);

        createFirstLine();
    }

    private void createFirstLine() {
        fromElement.addArc(this);
        toElement.addArc(this);

        Line line = createLine(fromElement.getShapeCenter(), toElement.getShapeCenter());
        lines.add(line);
        groupLines.getChildren().add(line);
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

        if (isHighlighted()) {
            line.setEffect(highlightEffect);
        }

        return line;
    }

    @Override
    public void onMousePressedHandler(MouseEvent mouseEvent) {
        super.onMousePressedHandler(mouseEvent);

        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            Point2D mousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            Line sourceLine = (Line) mouseEvent.getSource();
            createDragMark(sourceLine, mousePosition);
            lastAddedDragMark.onMousePressedHandler(mouseEvent);
        }
    }

    @Override
    public void onMouseDraggedHandler(MouseEvent mouseEvent) {
        super.onMouseDraggedHandler(mouseEvent);

        if (lastAddedDragMark != null) {
            lastAddedDragMark.onMouseDraggedHandler(mouseEvent);
        }
    }

    @Override
    public void onMouseReleasedHandler(MouseEvent mouseEvent) {
        super.onMouseReleasedHandler(mouseEvent);

        if (lastAddedDragMark != null) {
            lastAddedDragMark.onMouseReleasedHandler(mouseEvent);
            lastAddedDragMark = null;
        }
    }

    private void createDragMark(Line sourceLine, Point2D position) {
        int index = lines.indexOf(sourceLine);

        Line line = createLine(position.getX(), position.getY(), sourceLine.getEndX(), sourceLine.getEndY());
        lines.add(index + 1, line);
//        System.out.println(lines);
        groupLines.getChildren().add(line);

        sourceLine.setEndX(position.getX());
        sourceLine.setEndY(position.getY());
//        System.out.println(lines);

        lastAddedDragMark = new ArcDragMark(this, position.getX(), position.getY());
        if (isHighlighted()) {
            lastAddedDragMark.enableHighlight();
        }

        System.out.println("adding dragMark");
        lastAddedDragMark.addToParent(getGraphView());
        dragMarks.add(index, lastAddedDragMark);
    }

    public void dragMarkMovedHandler(ArcDragMark arcDragMark, Point2D center) {
        int index = dragMarks.indexOf(arcDragMark);
//        System.out.println("dragMark index " + index);
        Line lineTo = lines.get(index);
//        System.out.println("lineTo " + lineTo);
        lineTo.setEndX(center.getX());
        lineTo.setEndY(center.getY());
        Line lineFrom = lines.get(index + 1);
//        System.out.println("lineFrom " + lineFrom);
        lineFrom.setStartX(center.getX());
        lineFrom.setStartY(center.getY());

        if (index == 0) {
            setStart(fromElement.getBorderConnectionPoint(getLineEnd(lines.get(0))));
        }

        if (index == dragMarks.size() - 1) {
            setEnd(toElement.getBorderConnectionPoint(getLineStart(lines.get(index + 1))));
        }
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

    public void updateEnds(ConnectableGraphElement source) {
        setStart(fromElement.getBorderConnectionPoint(getLineEnd(lines.get(0))));
        setEnd(toElement.getBorderConnectionPoint(getLineStart(lines.get(lines.size() - 1))));
    }

    @Override
    public void enableHighlight() {
        super.enableHighlight();
        lines.forEach(line -> line.setEffect(highlightEffect));
        dragMarks.forEach(dragMark -> dragMark.enableHighlight());
        ending.getShape().setEffect(highlightEffect);
    }

    @Override
    public void disableHighlight() {
        super.disableHighlight();
        lines.forEach(line -> line.setEffect(null));
        dragMarks.forEach(dragMark -> dragMark.disableHighlight());
        ending.getShape().setEffect(null);
    }

    @Override
    public void addToParent(GraphView parent) {
        super.addToParent(parent);
        parent.addToLayerMiddle(groupSymbols);
        parent.addToLayerBottom(groupLines);
    }

    @Override
    public void removeFromParent(GraphView parent) {
        parent.removeFromLayerMiddle(groupSymbols);
        parent.removeFromLayerBottom(groupLines);
        fromElement.removeArc(this);
        toElement.removeArc(this);

        while (dragMarks.size() > 0) {
            dragMarks.get(0).removeFromParent(parent);
        }

        super.removeFromParent(parent);
    }

    @Override
    public void snapToGrid() {
        dragMarks.forEach(dragMark -> dragMark.snapToGrid());
    }

    @Override
    public void move(Point2D moveOffset) {
    }

    @Override
    public boolean canMove(Point2D offset) {
        return true;
    }

    public boolean hasHighlightedEnds() {
        return fromElement.isHighlighted() && toElement.isHighlighted();
    }

    public void removeStraightConnections() {
        for (int i = 0; i < dragMarks.size(); ) {
            Line lineTo = lines.get(i);
            Line lineFrom = lines.get(i + 1);
            if (areInLine(getLineStart(lineTo), getLineEnd(lineTo), getLineEnd(lineFrom))) {
                dragMarks.get(i).removeFromParent(getGraphView());
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

    public void removeDragMark(ArcDragMark dragMark) {
        int index = dragMarks.indexOf(dragMark);
        dragMarks.remove(index);

        Line lineTo = lines.get(index);
        Line lineFrom = lines.get(index + 1);
        lineTo.setEndX(lineFrom.getEndX());
        lineTo.setEndY(lineFrom.getEndY());
        lines.remove(lineFrom);
        groupLines.getChildren().remove(lineFrom);
    }

    @Override
    public Node getContextMenuNode() {
        return lines.get(0);
    }

    public Group getGroupSymbols() {
        return groupSymbols;
    }
}
