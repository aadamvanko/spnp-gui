package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TransitionController;
import cz.muni.fi.spnp.gui.viewmodel.InhibitorArcViewModel;

public class InhibitorArcController extends ArcController {
    public InhibitorArcController(PlaceController from, TransitionController to) {
        super(from, to);
        createView();
        createBindings(from, to);
    }

    private void createView() {
        ending = new ArcEndingCircle(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }

    private void createBindings(ConnectableGraphElement from, ConnectableGraphElement to) {
        InhibitorArcViewModel inhibitorArcViewModel = new InhibitorArcViewModel(from.getViewModel(), to.getViewModel());
        // binding to controls
        bindViewModel(inhibitorArcViewModel);
    }
}
