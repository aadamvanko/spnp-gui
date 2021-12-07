package cz.muni.fi.spnp.gui.components.menu.view.includes;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;

/**
 * Collapsable table view with the includes supporting operations add, edit and delete.
 */
public class IncludesCollapsableView extends GeneralItemsTableViewCollapsable<IncludeViewModel> {

    public IncludesCollapsableView(Model model) {
        super(model, "Includes", new IncludesTableView(model));
    }

}
