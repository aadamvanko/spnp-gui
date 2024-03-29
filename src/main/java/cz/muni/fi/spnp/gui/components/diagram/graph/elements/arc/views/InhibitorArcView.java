package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.InhibitorArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.TransitionView;

/**
 * Visual representation of the inhibitor arc.
 */
public class InhibitorArcView extends ArcView {
    public InhibitorArcView(GraphView graphView, InhibitorArcViewModel inhibitorArcViewModel, PlaceView from, TransitionView to) {
        super(graphView, inhibitorArcViewModel, from, to);
        createView();
        bindViewModel();
    }

    private void createView() {
        ending = new ArcEndingCircle(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }
}
