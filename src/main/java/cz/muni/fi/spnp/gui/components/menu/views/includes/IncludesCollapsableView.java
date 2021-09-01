package cz.muni.fi.spnp.gui.components.menu.views.includes;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableViewCollapsable;
import cz.muni.fi.spnp.gui.model.Model;

public class IncludesCollapsableView extends GeneralItemsTableViewCollapsable<IncludeViewModel> {

    public IncludesCollapsableView(Model model) {
        super(model, "Includes", new IncludesTableView(model));
    }

}
