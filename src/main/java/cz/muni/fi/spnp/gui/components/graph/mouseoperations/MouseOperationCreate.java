package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionController;
import javafx.scene.input.MouseEvent;

public class MouseOperationCreate extends MouseOperation {

    private final GraphElementType createElementType;

    public MouseOperationCreate(GraphView graphView) {
        super(graphView);
        createElementType = graphView.getCreateElementType();
    }

    @Override
    public void mousePressedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDraggedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleasedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        var position = graphView.getGridPane().screenToLocal(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        ConnectableGraphElement newElement = null;
        switch (createElementType) {
            case PLACE:
                newElement = new PlaceController(position.getX(), position.getY());
                break;

            case TIMED_TRANSITION:
                newElement = new TimedTransitionController(position.getX(), position.getY());
                break;

            case IMMEDIATE_TRANSITION:
                newElement = new ImmediateTransitionController(position.getX(), position.getY());
                break;

            default:
        }

        if (newElement != null) {
            newElement.addToParent(graphView);
            if (graphView.isSnappingEnabled()) {
                newElement.snapToGrid();
            }

            if (graphView.getCursorMode() == CursorMode.CREATE) {
                graphView.setCursorMode(CursorMode.VIEW);
            }
        }
    }
}
