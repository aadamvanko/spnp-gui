package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TransitionView;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.InhibitorArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.StandardArcViewModel;
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
            graphElementView = new PlaceView((PlaceViewModel) elementViewModel);
        } else if (elementViewModel instanceof ImmediateTransitionViewModel) {
            graphElementView = new ImmediateTransitionView((ImmediateTransitionViewModel) elementViewModel);
        } else if (elementViewModel instanceof TimedTransitionViewModel) {
            graphElementView = new TimedTransitionView((TimedTransitionViewModel) elementViewModel);
        } else if (elementViewModel instanceof StandardArcViewModel) {
            var standardArcViewModel = (StandardArcViewModel) elementViewModel;
            var elementFrom = (ConnectableGraphElementView) graphView.findElementViewByModel(standardArcViewModel.getFromViewModel());
            var elementTo = (ConnectableGraphElementView) graphView.findElementViewByModel(standardArcViewModel.getToViewModel());
            graphElementView = new StandardArcView(standardArcViewModel, elementFrom, elementTo);
        } else if (elementViewModel instanceof InhibitorArcViewModel) {
            var inhibitorArcViewModel = (InhibitorArcViewModel) elementViewModel;
            var elementFrom = (ConnectableGraphElementView) graphView.findElementViewByModel(inhibitorArcViewModel.getFromViewModel());
            var elementTo = (ConnectableGraphElementView) graphView.findElementViewByModel(inhibitorArcViewModel.getToViewModel());
            graphElementView = new InhibitorArcView(inhibitorArcViewModel, (PlaceView) elementFrom, (TransitionView) elementTo);
        }

        graphElementView.setGraphView(graphView);
        return graphElementView;
    }
}
