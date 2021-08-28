package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;

public class DefinesView extends GeneralItemsTableView<DefineViewModel> {

    public DefinesView(Model model) {
        super(model, "Defines", "define");

        addColumn("Name", "name");
        addColumn("Expression", "expression");
    }

    @Override
    protected DefineViewModel createViewModel() {
        return new DefineViewModel();
    }

    @Override
    protected GeneralItemView<DefineViewModel> createItemViewAdd(DefineViewModel defineViewModel) {
        return new DefineView(model, defineViewModel, null, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<DefineViewModel> createItemViewEdit(DefineViewModel defineViewModel) {
        var copy = viewModelCopyFactory.createCopy(defineViewModel);
        return new DefineView(model, copy, defineViewModel, ItemViewMode.EDIT);
    }

}

