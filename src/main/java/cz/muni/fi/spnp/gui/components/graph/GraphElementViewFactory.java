package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TransitionView;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;

public class GraphElementViewFactory {

    private final GraphView graphView;

    public GraphElementViewFactory(GraphView graphView) {
        this.graphView = graphView;
    }

    public GraphElementView createGraphElementView(ElementViewModel elementViewModel) {
        GraphElementView graphElementView = null;
        if (elementViewModel instanceof PlaceViewModel) {
            graphElementView = new PlaceView();
        } else if (elementViewModel instanceof ImmediateTransitionViewModel) {
            graphElementView = new ImmediateTransitionView();
        } else if (elementViewModel instanceof TimedTransitionViewModel) {
            graphElementView = new TimedTransitionView();
        } else if (elementViewModel instanceof StandardArcViewModel) {
            var arcViewModel = (ArcViewModel) elementViewModel;
            var elementFrom = (ConnectableGraphElementView) graphView.findElementViewByModel(arcViewModel.getFromViewModel());
            var elementTo = (ConnectableGraphElementView) graphView.findElementViewByModel(arcViewModel.getToViewModel());
            graphElementView = new StandardArcView(elementFrom, elementTo);
        } else if (elementViewModel instanceof InhibitorArcViewModel) {
            var arcViewModel = (ArcViewModel) elementViewModel;
            var elementFrom = (ConnectableGraphElementView) graphView.findElementViewByModel(arcViewModel.getFromViewModel());
            var elementTo = (ConnectableGraphElementView) graphView.findElementViewByModel(arcViewModel.getToViewModel());
            graphElementView = new InhibitorArcView((PlaceView) elementFrom, (TransitionView) elementTo);
        }

        graphElementView.setGraphView(graphView);
        graphElementView.bindViewModel(elementViewModel);
        return graphElementView;
    }
}
