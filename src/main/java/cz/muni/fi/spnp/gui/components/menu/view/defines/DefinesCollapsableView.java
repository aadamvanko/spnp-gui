package cz.muni.fi.spnp.gui.components.menu.view.defines;

import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;
import cz.muni.fi.spnp.gui.model.Model;

public class DefinesCollapsableView extends GeneralItemsTableViewCollapsable<DefineViewModel> {

    public DefinesCollapsableView(Model model) {
        super(model, "Defines", new DefinesTableView(model));
    }

}
