package cz.muni.fi.spnp.gui.components.diagram.graph;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.InhibitorArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.StandardArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.InhibitorArcView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.StandardArcView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.ImmediateTransitionView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.TimedTransitionView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.TransitionView;

/**
 * Creates the view for the given view model and graph view.
 */
public class GraphElementViewFactory {

    private final GraphView graphView;

    public GraphElementViewFactory(GraphView graphView) {
        this.graphView = graphView;
    }

    public GraphElementView createGraphElementView(ElementViewModel elementViewModel) {
        GraphElementView graphElementView = null;
        if (elementViewModel instanceof PlaceViewModel) {
            graphElementView = new PlaceView(graphView, (PlaceViewModel) elementViewModel);
        } else if (elementViewModel instanceof ImmediateTransitionViewModel) {
            graphElementView = new ImmediateTransitionView(graphView, (ImmediateTransitionViewModel) elementViewModel);
        } else if (elementViewModel instanceof TimedTransitionViewModel) {
            graphElementView = new TimedTransitionView(graphView, (TimedTransitionViewModel) elementViewModel);
        } else if (elementViewModel instanceof StandardArcViewModel) {
            var standardArcViewModel = (StandardArcViewModel) elementViewModel;
            var elementFrom = (ConnectableGraphElementView) graphView.findElementViewByModel(standardArcViewModel.getFromViewModel());
            var elementTo = (ConnectableGraphElementView) graphView.findElementViewByModel(standardArcViewModel.getToViewModel());
            graphElementView = new StandardArcView(graphView, standardArcViewModel, elementFrom, elementTo);
        } else if (elementViewModel instanceof InhibitorArcViewModel) {
            var inhibitorArcViewModel = (InhibitorArcViewModel) elementViewModel;
            var elementFrom = (ConnectableGraphElementView) graphView.findElementViewByModel(inhibitorArcViewModel.getFromViewModel());
            var elementTo = (ConnectableGraphElementView) graphView.findElementViewByModel(inhibitorArcViewModel.getToViewModel());
            graphElementView = new InhibitorArcView(graphView, inhibitorArcViewModel, (PlaceView) elementFrom, (TransitionView) elementTo);
        }

        return graphElementView;
    }

}
