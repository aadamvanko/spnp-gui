package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public abstract class GraphElementsOperationBase {

    protected final Model model;
    protected final DiagramViewModel diagramViewModel;

    public GraphElementsOperationBase(Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;
    }

}
