package cz.muni.fi.spnp.gui.components.menu.view.includes;

import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;

public class IncludeView extends GeneralItemView<IncludeViewModel> {

    public IncludeView(Model model, IncludeViewModel includeViewModel, IncludeViewModel original, ItemViewMode itemViewMode) {
        super(model, includeViewModel, original, itemViewMode);

        addRowTextField("Path:", includeViewModel.pathProperty());
    }

    @Override
    protected void copyToOriginal() {
        viewModelCopyFactory.copyTo(original, viewModel);
    }

}
