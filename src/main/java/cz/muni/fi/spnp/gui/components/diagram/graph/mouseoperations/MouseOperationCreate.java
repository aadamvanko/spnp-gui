package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;

/**
 * Base class for the mouse operations creating elements in the graph.
 */
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
        }
    }

}
