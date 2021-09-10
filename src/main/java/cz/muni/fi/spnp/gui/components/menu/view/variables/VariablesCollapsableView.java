package cz.muni.fi.spnp.gui.components.menu.view.variables;

import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;

public class VariablesCollapsableView extends GeneralItemsTableViewCollapsable<VariableViewModel> {

    public VariablesCollapsableView(Model model) {
        super(model, "Variables", new VariablesTableView(model));
    }

}
