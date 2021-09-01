package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableViewWindow;
import cz.muni.fi.spnp.gui.model.Model;

public class DefinesTableViewWindow extends GeneralItemsTableViewWindow<DefineViewModel> {

    public DefinesTableViewWindow(Model model) {
        super(model, "Defines", "define", new DefinesTableView(model));
        stage.setWidth(600);
    }

}

