package cz.muni.fi.spnp.gui.components.menu.view.defines;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;

/**
 * Window containing the table view with the defines supporting the operations add, edit and delete.
 */
public class DefinesTableViewWindow extends GeneralItemsTableViewWindow<DefineViewModel> {

    public DefinesTableViewWindow(Model model) {
        super(model, "Defines", new DefinesTableView(model));
        stage.setWidth(600);
    }

}

