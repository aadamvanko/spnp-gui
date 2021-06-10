package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TransitionController;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedTransitionViewModel;

public class GraphElementFactory {

    private final GraphView graphView;

    public GraphElementFactory(GraphView graphView) {
        this.graphView = graphView;
    }

    public void createGraphElement(ElementViewModel elementViewModel) {
        GraphElement graphElement = null;
        if (elementViewModel instanceof PlaceViewModel) {
            graphElement = new PlaceController();
        } else if (elementViewModel instanceof ImmediateTransitionViewModel) {
            graphElement = new ImmediateTransitionController();
        } else if (elementViewModel instanceof TimedTransitionViewModel) {
            graphElement = new TimedTransitionController();
        } else if (elementViewModel instanceof StandardArcViewModel) {
            var arcViewModel = (ArcViewModel) elementViewModel;
            var elementFrom = findConnectableGraphElement(arcViewModel.getFromViewModel().nameProperty().get());
            var elementTo = findConnectableGraphElement(arcViewModel.getToViewModel().nameProperty().get());
            graphElement = new StandardArcController(elementFrom, elementTo);
        } else if (elementViewModel instanceof InhibitorArcViewModel) {
            var arcViewModel = (ArcViewModel) elementViewModel;
            var elementFrom = findConnectableGraphElement(arcViewModel.getFromViewModel().nameProperty().get());
            var elementTo = findConnectableGraphElement(arcViewModel.getToViewModel().nameProperty().get());
            graphElement = new InhibitorArcController((PlaceController) elementFrom, (TransitionController) elementTo);
        }

        graphElement.addToParent(graphView);
        graphElement.bindViewModel(elementViewModel);
    }

    private ConnectableGraphElement findConnectableGraphElement(String name) {
        for (var element : graphView.getElements()) {
            if (element instanceof ConnectableGraphElement && element.getViewModel().nameProperty().get().equals(name)) {
                return (ConnectableGraphElement) element;
            }
        }
        return null;
    }
}
