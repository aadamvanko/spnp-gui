package cz.muni.fi.spnp.gui.components.diagram.graph;

import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.Movable;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.VisualElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public abstract class GraphElementView implements VisualElement, Movable {

    private static final double MIN_PADDING_FACTOR = 0.3;
    protected static DropShadow highlightEffect;

    static {
        highlightEffect = new DropShadow();
        highlightEffect.setColor(Color.RED);
        highlightEffect.setRadius(5);
    }

    protected GraphView graphView;
    protected ElementViewModel viewModel;
    private final ChangeListener<? super Boolean> onHighlightedChangedListener;

    public GraphElementView(GraphView graphView, ElementViewModel elementViewModel) {
        this.graphView = graphView;
        this.viewModel = elementViewModel;

        this.onHighlightedChangedListener = this::onHighlightedChangedListener;
    }

    protected void bindViewModel() {
        this.viewModel.highlightedProperty().addListener(this.onHighlightedChangedListener);
        onHighlightedChangedListener(null, null, viewModel.isHighlighted());
    }

    public void unbindViewModel() {
        this.viewModel.highlightedProperty().removeListener(this.onHighlightedChangedListener);

        this.viewModel = null;
        this.graphView = null;
    }

    public ElementViewModel getViewModel() {
        return viewModel;
    }

    protected abstract void enableHighlight();

    protected abstract void disableHighlight();

    private void onHighlightedChangedListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            enableHighlight();
        } else {
            disableHighlight();
        }
    }

    public void setGraphView(GraphView graphView) {
        this.graphView = graphView;
    }

    public GraphView getGraphView() {
        return graphView;
    }

    protected void registerMouseHandlers(Node node) {
        node.setOnMousePressed(this::onMousePressedHandler);
        node.setOnMouseDragged(this::onMouseDraggedHandler);
        node.setOnMouseReleased(this::onMouseReleasedHandler);
    }

    protected void unregisterMouseHandlers(Node node) {
        node.setOnMousePressed(null);
        node.setOnMouseDragged(null);
        node.setOnMouseReleased(null);
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

    protected Point2D preventNegativeCoordinates(Point2D point) {
        double x = Math.max(point.getX(), GridBackgroundPane.SPACING_X * MIN_PADDING_FACTOR);
        double y = Math.max(point.getY(), GridBackgroundPane.SPACING_Y * MIN_PADDING_FACTOR);
        return new Point2D(x, y);
    }

    protected void snapToGrid(Point2D center, Point2D leftTop) {
        Point2D snapOffset = calculateSnapOffset(center);
        Point2D movedCorner = leftTop.add(snapOffset);
        double moveCenterX = 0;
        if (!isValidX(movedCorner.getX())) {
            moveCenterX = GridBackgroundPane.SPACING_X;
        }

        double moveCenterY = 0;
        if (!isValidY(movedCorner.getY())) {
            moveCenterY = GridBackgroundPane.SPACING_Y;
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
        return x > GridBackgroundPane.SPACING_X * MIN_PADDING_FACTOR;
    }

    private boolean isValidY(double y) {
        return y > GridBackgroundPane.SPACING_Y * MIN_PADDING_FACTOR;
    }

    protected Point2D calculateSnapOffset(Point2D center) {
        Point2D snapPoint = calculateSnapPoint(center);
        Point2D offset = snapPoint.subtract(center);
        return offset;
    }

    private Point2D calculateSnapPoint(Point2D point) {
        int columnIndex = (int) (point.getX() / GridBackgroundPane.SPACING_X);
        int rowIndex = (int) (point.getY() / GridBackgroundPane.SPACING_Y);
        int remainderX = (int) point.getX() % GridBackgroundPane.SPACING_X;
        int remainderY = (int) point.getY() % GridBackgroundPane.SPACING_Y;

        if (remainderX > GridBackgroundPane.SPACING_X / 2) columnIndex++;
        if (remainderY > GridBackgroundPane.SPACING_Y / 2) rowIndex++;

        if (rowIndex == 0) rowIndex++;
        if (columnIndex == 0) columnIndex++;

        return new Point2D(columnIndex * GridBackgroundPane.SPACING_X, rowIndex * GridBackgroundPane.SPACING_Y);
    }

    public abstract Node getContextMenuNode();

}
