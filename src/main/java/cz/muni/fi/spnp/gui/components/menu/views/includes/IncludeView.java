package cz.muni.fi.spnp.gui.components.menu.views.includes;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;

public class IncludeView extends GeneralItemView<IncludeViewModel> {

    public IncludeView(IncludeViewModel includeViewModel, ItemViewMode itemViewMode) {
        super(includeViewModel, itemViewMode);

        addRowText("Path:", includeViewModel.pathProperty());
    }

}
