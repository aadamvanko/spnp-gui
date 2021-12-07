package cz.muni.fi.spnp.gui.components.menu.view.inputparameters;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;

/**
 * Window containing the table view with the input parameters supporting the operations add, edit and delete.
 */
public class InputParametersTableViewWindow extends GeneralItemsTableViewWindow<InputParameterViewModel> {

    public InputParametersTableViewWindow(Model model) {
        super(model, "Input Parameters", new InputParametersTableView(model));

        createView();
    }

    private void createView() {
        stage.setWidth(600);
    }

}
