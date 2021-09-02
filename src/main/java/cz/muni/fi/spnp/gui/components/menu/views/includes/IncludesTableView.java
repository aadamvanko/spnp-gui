package cz.muni.fi.spnp.gui.components.menu.views.includes;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;

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
