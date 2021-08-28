package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;

public class InputParametersView extends GeneralItemsTableView<InputParameterViewModel> {

    public InputParametersView(Model model) {
        super(model, "Input Parameters", "input parameter");

        addColumn("Name", "name");
        addColumn("Type", "type");
        addColumn("User prompt text", "userPromptText");

        stage.setWidth(600);
    }

    @Override
    protected InputParameterViewModel createViewModel() {
        return new InputParameterViewModel();
    }

    @Override
    protected GeneralItemView<InputParameterViewModel> createItemViewAdd(InputParameterViewModel inputParameterViewModel) {
        return new InputParameterView(model, inputParameterViewModel, null, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<InputParameterViewModel> createItemViewEdit(InputParameterViewModel inputParameterViewModel) {
        var copy = viewModelCopyFactory.createCopy(inputParameterViewModel);
        return new InputParameterView(model, copy, inputParameterViewModel, ItemViewMode.EDIT);
    }

}
