package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;

public class InputParametersView extends GeneralItemsTableView<InputParameterViewModel> {

    public InputParametersView() {
        super("Input Parameters", "input parameter");

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
        return new InputParameterView(inputParameterViewModel, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<InputParameterViewModel> createItemViewEdit(InputParameterViewModel inputParameterViewModel) {
        return new InputParameterView(inputParameterViewModel, ItemViewMode.EDIT);
    }
}
