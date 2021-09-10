package cz.muni.fi.spnp.gui.components.menu.view.variables;

import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;

public class InputParametersCollapsableView extends GeneralItemsTableViewCollapsable<InputParameterViewModel> {

    public InputParametersCollapsableView(Model model) {
        super(model, "Input Parameters", new InputParametersTableView(model));
    }

}
