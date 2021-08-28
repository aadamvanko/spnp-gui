package cz.muni.fi.spnp.gui.components.menu.views.includes;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;

public class IncludesView extends GeneralItemsTableView<IncludeViewModel> {

    public IncludesView(Model model) {
        super(model, "Includes", "include");

        addColumn("Path", "path");
    }

    @Override
    protected IncludeViewModel createViewModel() {
        return new IncludeViewModel();
    }

    @Override
    protected GeneralItemView<IncludeViewModel> createItemViewAdd(IncludeViewModel includeViewModel) {
        return new IncludeView(model, includeViewModel, null, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<IncludeViewModel> createItemViewEdit(IncludeViewModel includeViewModel) {
        var copy = viewModelCopyFactory.createCopy(includeViewModel);
        return new IncludeView(model, copy, includeViewModel, ItemViewMode.EDIT);
    }

}
