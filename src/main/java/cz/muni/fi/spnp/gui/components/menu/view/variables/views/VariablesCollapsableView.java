package cz.muni.fi.spnp.gui.components.menu.view.variables.views;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;

public class VariablesCollapsableView extends GeneralItemsTableViewCollapsable<VariableViewModel> {

    public VariablesCollapsableView(Model model) {
        super(model, "Variables", new VariablesTableView(model));
    }

}
