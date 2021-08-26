package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;

public class VariablesView extends GeneralItemsTableView<VariableViewModel> {

    public VariablesView() {
        super("Variables", "variable");

        addColumn("Name", "name");
        addColumn("Kind", "kind");
        addColumn("Type", "type");
        addColumn("Value", "value");

        stage.setWidth(600);
    }

    @Override
    protected VariableViewModel createViewModel() {
        return new VariableViewModel();
    }

    @Override
    protected GeneralItemView<VariableViewModel> createItemViewAdd(VariableViewModel variableViewModel) {
        return new VariableView(variableViewModel, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<VariableViewModel> createItemViewEdit(VariableViewModel variableViewModel) {
        return new VariableView(variableViewModel, ItemViewMode.EDIT);
    }
}
