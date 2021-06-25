package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues.BetaTransitionDistributionViewModel;
import javafx.geometry.Point2D;
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

        ConnectableViewModel newViewModel = createViewModel(graphElement, position);

        if (newViewModel != null) {
            var diagramViewModel = graphView.getDiagramViewModel();
            newViewModel.setDiagramViewModel(diagramViewModel);
            diagramViewModel.addElement(newViewModel);

            if (graphView.getCursorMode() == CursorMode.CREATE) {
                graphView.setCursorMode(CursorMode.VIEW);
            }
        }
    }

    private ConnectableViewModel createViewModel(GraphElement graphElement, Point2D position) {
        switch (createElementType) {
            case PLACE:
                var placeViewModel = new PlaceViewModel();
                placeViewModel.nameProperty().set("place");
                placeViewModel.positionXProperty().set(position.getX());
                placeViewModel.positionYProperty().set(position.getY());
                placeViewModel.numberOfTokensProperty().set("0");
                return placeViewModel;

            case TIMED_TRANSITION:
                var timedTransitionViewModel = new TimedTransitionViewModel();
                timedTransitionViewModel.nameProperty().set("timedTransition");
                timedTransitionViewModel.positionXProperty().set(position.getX());
                timedTransitionViewModel.positionYProperty().set(position.getY());
                timedTransitionViewModel.priorityProperty().set(0);
                timedTransitionViewModel.timedDistributionTypeProperty().set(TimedDistributionType.Beta);
                timedTransitionViewModel.setTransitionDistribution(new BetaTransitionDistributionViewModel(0.0, 0.0));
                return timedTransitionViewModel;

            case IMMEDIATE_TRANSITION:
                var immediateTransitionViewModel = new ImmediateTransitionViewModel();
                immediateTransitionViewModel.nameProperty().set("immediateTransition");
                immediateTransitionViewModel.positionXProperty().set(position.getX());
                immediateTransitionViewModel.positionYProperty().set(position.getY());
                immediateTransitionViewModel.priorityProperty().set(0);
                return immediateTransitionViewModel;

            default:
                throw new IllegalStateException("SHOULD NOT HAPPEN");
        }
    }
}
