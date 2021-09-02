package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;

public class DefinesTableView extends GeneralItemsTableView<DefineViewModel> {

    public DefinesTableView(Model model) {
        super(model, "define");

        addColumn("Name", "name");
        addColumn("Expression", "expression");
    }

    @Override
    public DefineViewModel createViewModel() {
        return new DefineViewModel();
    }

    @Override
    public GeneralItemView<DefineViewModel> createItemViewAdd(DefineViewModel defineViewModel) {
        return new DefineView(model, defineViewModel, null, ItemViewMode.ADD);
    }

    @Override
    public GeneralItemView<DefineViewModel> createItemViewEdit(DefineViewModel defineViewModel) {
        var copy = viewModelCopyFactory.createCopy(defineViewModel);
        return new DefineView(model, copy, defineViewModel, ItemViewMode.EDIT);
    }

}
