package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;

public class DefinesView extends GeneralItemsTableView<DefineViewModel> {

    public DefinesView() {
        super("Defines", "define");

        addColumn("Name", "name");
        addColumn("Expression", "expression");
    }

    @Override
    protected DefineViewModel createViewModel() {
        return new DefineViewModel();
    }

    @Override
    protected GeneralItemView<DefineViewModel> createItemViewAdd(DefineViewModel defineViewModel) {
        return new DefineView(defineViewModel, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<DefineViewModel> createItemViewEdit(DefineViewModel defineViewModel) {
        return new DefineView(defineViewModel, ItemViewMode.EDIT);
    }
}

