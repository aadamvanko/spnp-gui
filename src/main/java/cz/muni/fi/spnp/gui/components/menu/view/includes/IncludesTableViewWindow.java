package cz.muni.fi.spnp.gui.components.menu.view.includes;

import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableViewWindow;
import cz.muni.fi.spnp.gui.model.Model;

public class IncludesTableViewWindow extends GeneralItemsTableViewWindow<IncludeViewModel> {

    public IncludesTableViewWindow(Model model) {
        super(model, "Includes", new IncludesTableView(model));
        stage.setWidth(600);
    }

}
