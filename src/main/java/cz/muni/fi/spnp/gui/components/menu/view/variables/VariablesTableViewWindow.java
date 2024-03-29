package cz.muni.fi.spnp.gui.components.menu.view.variables;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;

public class VariablesTableViewWindow extends GeneralItemsTableViewWindow<VariableViewModel> {

    public VariablesTableViewWindow(Model model) {
        super(model, "Variables", new VariablesTableView(model));

        createView();
    }

    private void createView() {
        stage.setWidth(600);
    }

}
