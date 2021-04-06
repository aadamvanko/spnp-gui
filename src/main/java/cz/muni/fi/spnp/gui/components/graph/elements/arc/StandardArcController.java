package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.viewmodel.StandardArcViewModel;

public class StandardArcController extends ArcController {

    public StandardArcController(ConnectableGraphElement from, ConnectableGraphElement to) {
        super(from, to);
        createView();
        createBindings(from, to);
    }

    private void createView() {
        ending = new ArcEndingArrow(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }

    private void createBindings(ConnectableGraphElement from, ConnectableGraphElement to) {
        StandardArcViewModel standardArcViewModel = new StandardArcViewModel(from.getViewModel(), to.getViewModel());
        // binding to controls
        bindViewModel(standardArcViewModel);
    }

}
