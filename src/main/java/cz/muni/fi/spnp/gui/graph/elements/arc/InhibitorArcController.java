package cz.muni.fi.spnp.gui.graph.elements.arc;

import cz.muni.fi.spnp.gui.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.graph.elements.transition.TransitionController;

public class InhibitorArcController extends ArcController {
    public InhibitorArcController(PlaceController from, TransitionController to) {
        super(from, to);

        ending = new ArcEndingCircle(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }
}
