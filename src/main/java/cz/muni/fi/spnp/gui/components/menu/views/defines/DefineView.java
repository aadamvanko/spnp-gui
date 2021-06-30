package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;

public class DefineView extends GeneralItemView<DefineViewModel> {

    public DefineView(DefineViewModel defineViewModel, ItemViewMode itemViewMode) {
        super(defineViewModel, itemViewMode);

        addRowText("Name:", defineViewModel.nameProperty());
        addRowText("Expression:", defineViewModel.expressionProperty());
    }
}
