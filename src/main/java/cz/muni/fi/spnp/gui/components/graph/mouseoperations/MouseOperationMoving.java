package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseOperationMoving extends MouseOperation {

    private Point2D oldMousePosition;

    public MouseOperationMoving(GraphView graphView) {
        super(graphView);
        System.out.println("moving operation created");
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        if (graphView.getSelected().size() <= 1 || !graphView.getSelected().contains(graphElementView)) {
            graphView.select(graphElementView);
        }

        oldMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        Point2D mousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        double scale = graphView.getZoomableScrollPane().getScaleValue();
        Point2D offset = mousePosition.subtract(oldMousePosition).multiply(1 / scale);
        oldMousePosition = mousePosition;
        graphView.moveSelected(offset);
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        graphView.moveSelectedEnded();
        graphView.adjustCanvasSize();
    }
}
