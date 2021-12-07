package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.twovalues.BetaTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MouseOperationCreateConnectable extends MouseOperationCreate {

    private final Model model;

    public MouseOperationCreateConnectable(GraphView graphView, Model model) {
        super(graphView);
        this.model = model;
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        var position = graphView.getGridPane().screenToLocal(mouseEvent.getScreenX(), mouseEvent.getScreenY());

        ConnectableViewModel newViewModel = createViewModel(graphElementView, position);

        if (newViewModel != null) {
            var diagramViewModel = graphView.getDiagramViewModel();
            renameIfNeeded(graphView.getDiagramViewModel(), newViewModel);
            diagramViewModel.getElements().add(newViewModel);

            if (model.getCursorMode() == CursorMode.CREATE) {
                model.cursorModeProperty().set(CursorMode.VIEW);
            }
        }
    }

    private ConnectableViewModel createViewModel(GraphElementView graphElementView, Point2D position) {
        switch (model.getCreateElementType()) {
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
                timedTransitionViewModel.setTransitionDistribution(new BetaTransitionDistributionViewModel("0.0", "0.0"));
                return timedTransitionViewModel;

            case IMMEDIATE_TRANSITION:
                var immediateTransitionViewModel = new ImmediateTransitionViewModel();
                immediateTransitionViewModel.nameProperty().set("immediateTransition");
                immediateTransitionViewModel.positionXProperty().set(position.getX());
                immediateTransitionViewModel.positionYProperty().set(position.getY());
                immediateTransitionViewModel.priorityProperty().set(0);
                return immediateTransitionViewModel;

            default:
                throw new IllegalStateException("SHOULD NOT HAPPEN " + model.getCreateElementType());
        }
    }
}
