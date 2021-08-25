package cz.muni.fi.spnp.gui.components.menu.views.includes;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;

public class IncludesView extends GeneralItemsTableView<IncludeViewModel> {

    public IncludesView() {
        super("Includes", "include");

        addColumn("Path", "path");
    }

    @Override
    protected IncludeViewModel createViewModel() {
        return new IncludeViewModel();
    }

    @Override
    protected GeneralItemView<IncludeViewModel> createItemViewAdd(IncludeViewModel includeViewModel) {
        return new IncludeView(includeViewModel, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<IncludeViewModel> createItemViewEdit(IncludeViewModel includeViewModel) {
        return new IncludeView(includeViewModel, ItemViewMode.EDIT);
    }

}
