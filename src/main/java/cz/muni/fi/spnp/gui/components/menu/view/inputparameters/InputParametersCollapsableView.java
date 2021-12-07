package cz.muni.fi.spnp.gui.components.menu.view.inputparameters;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;

public class InputParametersCollapsableView extends GeneralItemsTableViewCollapsable<InputParameterViewModel> {

    public InputParametersCollapsableView(Model model) {
        super(model, "Input Parameters", new InputParametersTableView(model));
    }

}
