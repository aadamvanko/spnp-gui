package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableViewWindow;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;

public class InputParametersTableViewWindow extends GeneralItemsTableViewWindow<InputParameterViewModel> {

    public InputParametersTableViewWindow(Model model) {
        super(model, "Input Parameters", "input parameter", new InputParametersTableView(model));

        createView();
    }

    private void createView() {
        stage.setWidth(600);
    }

}
