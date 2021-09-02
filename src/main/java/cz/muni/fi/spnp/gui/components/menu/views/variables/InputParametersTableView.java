package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;

public class InputParametersTableView extends GeneralItemsTableView<InputParameterViewModel> {

    public InputParametersTableView(Model model) {
        super(model, "input parameter");

        addColumn("Name", "name");
        addColumn("Type", "type");
        addColumn("User prompt text", "userPromptText");
    }

    @Override
    public InputParameterViewModel createViewModel() {
        return new InputParameterViewModel();
    }

    @Override
    public GeneralItemView<InputParameterViewModel> createItemViewAdd(InputParameterViewModel inputParameterViewModel) {
        return new InputParameterView(model, inputParameterViewModel, null, ItemViewMode.ADD);
    }

    @Override
    public GeneralItemView<InputParameterViewModel> createItemViewEdit(InputParameterViewModel inputParameterViewModel) {
        var copy = viewModelCopyFactory.createCopy(inputParameterViewModel);
        return new InputParameterView(model, copy, inputParameterViewModel, ItemViewMode.EDIT);
    }

}
