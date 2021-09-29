package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.Movable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class MouseOperationMoving extends MouseOperation {

    private Point2D oldMousePosition;

    public MouseOperationMoving(GraphView graphView) {
        super(graphView);
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        var diagramViewModel = graphView.getDiagramViewModel();
        if (diagramViewModel.getSelected().size() <= 1 || !diagramViewModel.getSelected().contains(graphElementView.getViewModel())) {
            diagramViewModel.select(List.of(graphElementView.getViewModel()));
        }

        oldMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        Point2D mousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        double scale = graphView.getZoomableScrollPane().getScaleValue();
        Point2D offset = mousePosition.subtract(oldMousePosition).multiply(1 / scale);
        oldMousePosition = mousePosition;

        var selectedViews = graphView.getSelectedViews();
        if (selectedViews.stream().allMatch(element -> element.canMove(offset))) {
            selectedViews.forEach(element -> {
                element.move(offset);
            });
        }
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        if (graphView.getDiagramViewModel().isGridSnapping()) {
            graphView.getSelectedViews().forEach(Movable::snapToGrid);
        }

        graphView.getSelectedViews().forEach(view -> view.preservePosition());
        graphView.adjustCanvasSize();
    }
}
