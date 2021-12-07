package cz.muni.fi.spnp.gui.components.menu.view.defines;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewCollapsable;

/**
 * Collapsable table view with the defines supporting operations add, edit and delete.
 */
public class DefinesCollapsableView extends GeneralItemsTableViewCollapsable<DefineViewModel> {

    public DefinesCollapsableView(Model model) {
        super(model, "Defines", new DefinesTableView(model));
    }

}
