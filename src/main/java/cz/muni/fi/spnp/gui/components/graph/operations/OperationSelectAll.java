package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelUtils;

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
