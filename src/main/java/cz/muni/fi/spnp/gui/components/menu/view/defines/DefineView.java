package cz.muni.fi.spnp.gui.components.menu.view.defines;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;

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
