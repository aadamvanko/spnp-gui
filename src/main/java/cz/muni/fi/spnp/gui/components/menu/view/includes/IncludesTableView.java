package cz.muni.fi.spnp.gui.components.menu.view.includes;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;

public class IncludesTableView extends GeneralItemsTableView<IncludeViewModel> {

    public IncludesTableView(Model model) {
        super(model, "include");

        addColumn("Path", "path");
    }

    @Override
    public IncludeViewModel createViewModel() {
        return new IncludeViewModel();
    }

    @Override
    public GeneralItemView<IncludeViewModel> createItemViewAdd(IncludeViewModel includeViewModel) {
        return new IncludeView(model, includeViewModel, null, ItemViewMode.ADD);
    }

    @Override
    public GeneralItemView<IncludeViewModel> createItemViewEdit(IncludeViewModel includeViewModel) {
        var copy = viewModelCopyFactory.createCopy(includeViewModel);
        return new IncludeView(model, copy, includeViewModel, ItemViewMode.EDIT);
    }

}
