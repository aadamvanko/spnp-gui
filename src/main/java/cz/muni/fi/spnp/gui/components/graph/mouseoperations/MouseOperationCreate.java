package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.TimedTransitionViewModel;
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

        ConnectableElementViewModel newViewModel = null;
        switch (createElementType) {
            case PLACE:
                newViewModel = new PlaceViewModel("place", position.getX(), position.getY(), 0);
                break;

            case TIMED_TRANSITION:
                newViewModel = new TimedTransitionViewModel("timedTransition", position.getX(), position.getY(), 0, TransitionDistributionType.Constant);
                break;

            case IMMEDIATE_TRANSITION:
                newViewModel = new ImmediateTransitionViewModel("immediateTransition", position.getX(), position.getY(), 1);
                break;

            default:
                System.out.println("WHAAAAAAAAAAAAAAAAAAAAAAT");
        }

        if (newViewModel != null) {
            var diagramViewModel = graphView.getDiagramViewModel();
            newViewModel.setDiagramViewModel(diagramViewModel);
            diagramViewModel.addElement(newViewModel);

            if (graphView.getCursorMode() == CursorMode.CREATE) {
                graphView.setCursorMode(CursorMode.VIEW);
            }
        }
    }
}
