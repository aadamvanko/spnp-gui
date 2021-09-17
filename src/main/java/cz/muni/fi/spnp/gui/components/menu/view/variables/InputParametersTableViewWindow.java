package cz.muni.fi.spnp.gui.components.menu.view.variables;

import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;

public class InputParametersTableViewWindow extends GeneralItemsTableViewWindow<InputParameterViewModel> {

    public InputParametersTableViewWindow(Model model) {
        super(model, "Input Parameters", new InputParametersTableView(model));

        createView();
    }

    private void createView() {
        stage.setWidth(600);
    }

}