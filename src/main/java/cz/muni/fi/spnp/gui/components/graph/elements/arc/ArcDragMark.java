package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ArcDragMark extends GraphElement implements MouseSelectable {

    private final ArcController arc;
    private final Rectangle rectangle;

    public ArcDragMark(ArcController arc, double x, double y) {
        this.arc = arc;

        rectangle = new Rectangle();
        rectangle.setWidth(7);
        rectangle.setHeight(7);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        setCenterPosition(x, y);
        rectangle.setSmooth(true);
    }

    public Rectangle getShape() {
        return rectangle;
    }

    public Point2D getCenterPosition() {
        return new Point2D(rectangle.getTranslateX() + rectangle.getWidth() / 2, rectangle.getTranslateY() + rectangle.getHeight() / 2);
    }

    private void setCenterPosition(double x, double y) {
        rectangle.setTranslateX(x - rectangle.getWidth() / 2);
        rectangle.setTranslateY(y - rectangle.getHeight() / 2);
    }

    private Point2D getShapePosition() {
        return new Point2D(rectangle.getTranslateX(), rectangle.getTranslateY());
    }

    @Override
    public void addToParent(GraphView parent) {
        super.addToParent(parent);
        arc.getGroupSymbols().getChildren().add(rectangle);
        registerMouseHandlers(rectangle);
    }

    @Override
    public void removeFromParent(GraphView parent) {
        super.removeFromParent(parent);

        arc.removeDragMark(this);
        arc.getGroupSymbols().getChildren().remove(rectangle);
        unregisterMouseHandlers(rectangle);
    }

    @Override
    public void onMouseReleasedHandler(MouseEvent mouseEvent) {
        super.onMouseReleasedHandler(mouseEvent);
        arc.removeStraightConnections();
    }

    @Override
    public void snapToGrid() {
        snapToGrid(getShapeCenter(), getShapePosition());
    }

    @Override
    public void move(Point2D offset) {
        moveViaTranslate(rectangle, offset);
        arc.dragMarkMovedHandler(this, getCenterPosition());
    }

    @Override
    public boolean canMove(Point2D offset) {
        return canMove(getShapeCenter(), getShapePosition(), offset);
    }

    @Override
    public Point2D getShapeCenter() {
        double x = rectangle.getTranslateX() + rectangle.getWidth() / 2;
        double y = rectangle.getTranslateY() + rectangle.getHeight() / 2;
        return new Point2D(x, y);
    }

    @Override
    public Point2D rightBottomCorner() {
        Point2D rightBottom = new Point2D(rectangle.getBoundsInLocal().getMaxX(), rectangle.getBoundsInLocal().getMaxY());
        return getShapePosition().add(rightBottom);
    }

    @Override
    public void enableHighlight() {
        super.enableHighlight();
        rectangle.setEffect(highlightEffect);
    }

    @Override
    public void disableHighlight() {
        super.disableHighlight();
        rectangle.setEffect(null);
    }

    @Override
    public Node getContextMenuNode() {
        return rectangle;
    }
}
