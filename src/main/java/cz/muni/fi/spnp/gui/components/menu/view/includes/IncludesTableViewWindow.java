package cz.muni.fi.spnp.gui.components.menu.view.includes;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;

public class IncludesTableViewWindow extends GeneralItemsTableViewWindow<IncludeViewModel> {

    public IncludesTableViewWindow(Model model) {
        super(model, "Includes", new IncludesTableView(model));
        stage.setWidth(600);
    }

}
