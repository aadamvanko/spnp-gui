package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableViewWindow;
import cz.muni.fi.spnp.gui.model.Model;

public class VariablesTableViewWindow extends GeneralItemsTableViewWindow {

    public VariablesTableViewWindow(Model model) {
        super(model, "Variables", "variable", new VariablesTableView(model));

        createView();
    }

    private void createView() {
        stage.setWidth(600);
    }

}
