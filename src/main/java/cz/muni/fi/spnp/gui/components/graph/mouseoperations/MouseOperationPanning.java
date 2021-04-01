package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.canvas.ZoomableScrollPane;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

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

        System.out.println("panning operation created");

        zoomableScrollPane = graphView.getZoomableScrollPane();
    }

    @Override
    public void mousePressedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        oldCursor = zoomableScrollPane.getScene().getCursor();
        zoomableScrollPane.getScene().setCursor(Cursor.MOVE);
        System.out.println(zoomableScrollPane.getHmin() + " " + zoomableScrollPane.getHmax() + " " + zoomableScrollPane.getHvalue());
        oldMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    @Override
    public void mouseDraggedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
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
    public void mouseReleasedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        zoomableScrollPane.getScene().setCursor(oldCursor);
    }

    @Override
    public void finish() {
        zoomableScrollPane.getScene().setCursor(oldCursor);
    }
}
