package cz.muni.fi.spnp.gui.components.menu.view.inputparameters;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;

/**
 * Collapsable table view with the input parameters supporting operations add, edit and delete.
 */
public class InputParametersCollapsableView extends GeneralItemsTableViewCollapsable<InputParameterViewModel> {

    public InputParametersCollapsableView(Model model) {
        super(model, "Input Parameters", new InputParametersTableView(model));
    }

}
