package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;

public abstract class MouseOperationCreate extends MouseOperation {

    public MouseOperationCreate(GraphView graphView) {
        super(graphView);
    }

    protected void renameIfNeeded(DiagramViewModel diagramViewModel, ElementViewModel elementViewModel) {
        int id = 1;
        var oldName = elementViewModel.getName();
        while (diagramViewModel.containsElementNameType(elementViewModel)) {
            var suffix = String.format("_%d", id);
            elementViewModel.nameProperty().set(oldName + suffix);
            id++;
            System.out.println(elementViewModel);
        }
    }

}
