package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;

/**
 * Select all operation to select all elements in the diagram.
 */
public class OperationSelectAll extends GraphElementsOperationBase {

    public OperationSelectAll(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        var allViewModels = ViewModelUtils.includeDragPoints(diagramViewModel.getElements());
        diagramViewModel.select(allViewModels);
    }

}
