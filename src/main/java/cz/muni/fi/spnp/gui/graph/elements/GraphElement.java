package cz.muni.fi.spnp.gui.graph.elements;

import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.canvas.GridPane;
import cz.muni.fi.spnp.gui.graph.interfaces.Highlightable;
import cz.muni.fi.spnp.gui.graph.interfaces.Movable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public abstract class GraphElement implements Highlightable, Movable {

    private static final double MIN_PADDING_FACTOR = 0.3;
    protected static DropShadow highlightEffect;

    static {
        highlightEffect = new DropShadow();
        highlightEffect.setColor(Color.RED);
        highlightEffect.setRadius(5);
    }

    private GraphView graphView;
    private boolean highlighted;

    public GraphView getGraphView() {
        return graphView;
    }

    public void addToParent(GraphView parent) {
        System.out.println("addToParent called");
        parent.addElement(this);
        graphView = parent;
    }

    public void removeFromParent(GraphView parent) {
        System.out.println("removeFromParent called");
        parent.removeElement(this);
        graphView = null;
    }

    protected void registerMouseHandlers(Node node) {
        node.setOnMousePressed(this::onMousePressedHandler);
        node.setOnMouseDragged(this::onMouseDraggedHandler);
        node.setOnMouseReleased(this::onMouseReleasedHandler);
    }

    public void onMousePressedHandler(MouseEvent mouseEvent) {
        mouseEvent.consume();

        graphView.graphElementPressed(this, mouseEvent);
    }

    public void onMouseDraggedHandler(MouseEvent mouseEvent) {
        mouseEvent.consume();

        graphView.graphElementDragged(this, mouseEvent);
    }

    public void onMouseReleasedHandler(MouseEvent mouseEvent) {
        mouseEvent.consume();

        graphView.graphElementReleased(this, mouseEvent);
    }

    @Override
    public void enableHighlight() {
        highlighted = true;
    }

    @Override
    public void disableHighlight() {
        highlighted = false;
    }

    @Override
    public boolean isHighlighted() {
        return highlighted;
    }

    protected void moveViaTranslate(Node node, Point2D offset) {
        Point2D old = new Point2D(node.getTranslateX(), node.getTranslateY());
        Point2D newPos = preventNegativeCoordinates(old.add(offset));
        node.setTranslateX(newPos.getX());
        node.setTranslateY(newPos.getY());
    }

    private Point2D preventNegativeCoordinates(Point2D point) {
        double x = Math.max(point.getX(), GridPane.SPACING_X * MIN_PADDING_FACTOR);
        double y = Math.max(point.getY(), GridPane.SPACING_Y * MIN_PADDING_FACTOR);
        return new Point2D(x, y);
    }

    protected void snapToGrid(Point2D center, Point2D leftTop) {
        Point2D snapOffset = calculateSnapOffset(center);
        Point2D movedCorner = leftTop.add(snapOffset);
        double moveCenterX = 0;
        if (!isValidX(movedCorner.getX())) {
            moveCenterX = GridPane.SPACING_X;
        }

        double moveCenterY = 0;
        if (!isValidY(movedCorner.getY())) {
            moveCenterY = GridPane.SPACING_Y;
        }

        Point2D movedCenter = new Point2D(moveCenterX, moveCenterY);
        snapOffset = calculateSnapOffset(center.add(movedCenter)).add(movedCenter);

        move(snapOffset);
    }

    protected boolean canMove(Point2D center, Point2D leftTop, Point2D offset) {
        Point2D newCenter = center.add(offset);
        Point2D newLeftTop = leftTop.add(offset);
        return isValidPosition(newCenter) && isValidPosition(newLeftTop);
    }

    private boolean isValidPosition(Point2D pos) {
        return isValidX(pos.getX()) && isValidY(pos.getY());
    }

    private boolean isValidX(double x) {
        return x > GridPane.SPACING_X * MIN_PADDING_FACTOR;
    }

    private boolean isValidY(double y) {
        return y > GridPane.SPACING_Y * MIN_PADDING_FACTOR;
    }

    protected Point2D calculateSnapOffset(Point2D center) {
        Point2D snapPoint = calculateSnapPoint(center);
        Point2D offset = snapPoint.subtract(center);
        return offset;
    }

    private Point2D calculateSnapPoint(Point2D point) {
        int columnIndex = (int) (point.getX() / GridPane.SPACING_X);
        int rowIndex = (int) (point.getY() / GridPane.SPACING_Y);
        int remainderX = (int) point.getX() % GridPane.SPACING_X;
        int remainderY = (int) point.getY() % GridPane.SPACING_Y;

        if (remainderX > GridPane.SPACING_X / 2) columnIndex++;
        if (remainderY > GridPane.SPACING_Y / 2) rowIndex++;

        if (rowIndex == 0) rowIndex++;
        if (columnIndex == 0) columnIndex++;

        return new Point2D(columnIndex * GridPane.SPACING_X, rowIndex * GridPane.SPACING_Y);
    }

    public abstract Node getContextMenuNode();
}
