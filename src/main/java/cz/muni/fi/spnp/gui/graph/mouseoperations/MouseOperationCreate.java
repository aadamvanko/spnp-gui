package cz.muni.fi.spnp.gui.graph.mouseoperations;

import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.graph.elements.transition.TimedTransitionController;
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
        }
    }
}
