package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.viewmodel.DragPointViewModel;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DragPointView extends GraphElementView implements MouseSelectable {

    private final ArcView arc;
    private final Rectangle rectangle;

    public DragPointView(GraphView graphView, ArcView arcView, DragPointViewModel dragPointViewModel) {
        super(graphView, dragPointViewModel);

        this.arc = arcView;

        rectangle = new Rectangle();
        rectangle.setWidth(7);
        rectangle.setHeight(7);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        setCenterPosition(dragPointViewModel.getPositionX(), dragPointViewModel.getPositionY());
        rectangle.setSmooth(true);

        bindViewModel();
    }

    @Override
    public DragPointViewModel getViewModel() {
        return (DragPointViewModel) viewModel;
    }

    private void bindViewModel() {
        rectangle.translateXProperty().bindBidirectional(getViewModel().positionXProperty());
        rectangle.translateYProperty().bindBidirectional(getViewModel().positionYProperty());
    }

    @Override
    public void unbindViewModel() {
        rectangle.translateXProperty().unbindBidirectional(getViewModel().positionXProperty());
        rectangle.translateYProperty().unbindBidirectional(getViewModel().positionYProperty());
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
    public Node getBottomLayerContainer() {
        return null;
    }

    @Override
    public Node getMiddleLayerContainer() {
        return rectangle;
    }

    @Override
    public Node getTopLayerContainer() {
        return null;
    }

    @Override
    public void addedToParent() {
        registerMouseHandlers(rectangle);
    }

    @Override
    public void removedFromParent() {
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
        moveViaTranslate(offset);
        arc.dragPointMovedHandler(this, getCenterPosition());
    }

    protected void moveViaTranslate(Point2D offset) {
        Point2D old = new Point2D(getViewModel().positionXProperty().get(), getViewModel().positionYProperty().get());
        Point2D newPos = preventNegativeCoordinates(old.add(offset));

        getViewModel().positionXProperty().set(newPos.getX());
        getViewModel().positionYProperty().set(newPos.getY());
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
