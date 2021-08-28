package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;

public class DefineView extends GeneralItemView<DefineViewModel> {

    public DefineView(Model model, DefineViewModel defineViewModel, DefineViewModel original, ItemViewMode itemViewMode) {
        super(model, defineViewModel, original, itemViewMode);

        addRowTextField("Name:", defineViewModel.nameProperty());
        addRowTextField("Expression:", defineViewModel.expressionProperty());
    }

    @Override
    protected void copyToOriginal() {
        viewModelCopyFactory.copyTo(original, viewModel);
    }

}
