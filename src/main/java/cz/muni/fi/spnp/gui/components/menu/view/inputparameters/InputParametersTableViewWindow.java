package cz.muni.fi.spnp.gui.components.menu.view.inputparameters;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;

public class InputParametersTableViewWindow extends GeneralItemsTableViewWindow<InputParameterViewModel> {

    public InputParametersTableViewWindow(Model model) {
        super(model, "Input Parameters", new InputParametersTableView(model));

        createView();
    }

    private void createView() {
        stage.setWidth(600);
    }

}
