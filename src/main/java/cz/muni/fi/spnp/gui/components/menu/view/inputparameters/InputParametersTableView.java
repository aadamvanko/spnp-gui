package cz.muni.fi.spnp.gui.components.menu.view.inputparameters;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;

/**
 * Table view with the input parameters supporting operations add, edit and delete.
 */
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
