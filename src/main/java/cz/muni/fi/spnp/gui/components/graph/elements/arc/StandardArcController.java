package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElement;

public class StandardArcController extends ArcController {

    public StandardArcController(ConnectableGraphElement from, ConnectableGraphElement to) {
        super(from, to);

        ending = new ArcEndingArrow(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }

}
