package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseOperationMoving extends MouseOperation {

    private Point2D oldMousePosition;

    public MouseOperationMoving(GraphView graphView) {
        super(graphView);
        System.out.println("moving operation created");
    }

    @Override
    public void mousePressedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        if (graphView.getSelected().size() <= 1 || !graphView.getSelected().contains(graphElement)) {
            graphView.select(graphElement);
        }

        oldMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    @Override
    public void mouseDraggedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        Point2D mousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        double scale = graphView.getZoomableScrollPane().getScaleValue();
        Point2D offset = mousePosition.subtract(oldMousePosition).multiply(1 / scale);
        oldMousePosition = mousePosition;
        graphView.moveSelected(offset);
    }

    @Override
    public void mouseReleasedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        graphView.moveSelectedEnded();
        graphView.adjustCanvasSize();
    }
}
