package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.ZoomableScrollPane;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

/**
 * Mouse operations used for panning of the graph canvas.
 */
public class MouseOperationPanning extends MouseOperation {

    private static final double PANNING_SPEED;

    static {
        PANNING_SPEED = 3;
    }

    private final ZoomableScrollPane zoomableScrollPane;
    private Point2D oldMousePosition;
    private Cursor oldCursor;

    public MouseOperationPanning(GraphView graphView) {
        super(graphView);

        zoomableScrollPane = graphView.getZoomableScrollPane();
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        oldCursor = zoomableScrollPane.getScene().getCursor();
        zoomableScrollPane.getScene().setCursor(Cursor.MOVE);
        oldMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        Point2D mousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        double scale = graphView.getZoomableScrollPane().getScaleValue();
        var groupBounds = zoomableScrollPane.getZoomGroup().getBoundsInLocal();
        Point2D offset = mousePosition.subtract(oldMousePosition).multiply(1 / scale);
        offset = new Point2D(offset.getX() / groupBounds.getWidth(), offset.getY() / groupBounds.getHeight());
        offset = offset.multiply(PANNING_SPEED);
        zoomableScrollPane.setHvalue(zoomableScrollPane.getHvalue() - offset.getX());
        zoomableScrollPane.setVvalue(zoomableScrollPane.getVvalue() - offset.getY());
        oldMousePosition = mousePosition;
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        zoomableScrollPane.getScene().setCursor(oldCursor);
    }

    @Override
    public void finish() {
        zoomableScrollPane.getScene().setCursor(oldCursor);
    }
}
