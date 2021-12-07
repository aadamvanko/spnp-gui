package cz.muni.fi.spnp.gui.components.menu.view.inputparameters;

import cz.muni.fi.spnp.gui.components.common.DialogMessages;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableDataType;

public class InputParameterView extends GeneralItemView<InputParameterViewModel> {

    public InputParameterView(Model model, InputParameterViewModel inputParameterViewModel, InputParameterViewModel original, ItemViewMode itemViewMode) {
        super(model, inputParameterViewModel, original, itemViewMode);

        addRowTextField("Name:", inputParameterViewModel.nameProperty());
        addRowEnumChoiceBox("Type:", inputParameterViewModel.typeProperty(), VariableDataType.class);
        addRowTextField("User prompt text:", inputParameterViewModel.userPromptTextProperty());
    }

    @Override
    protected boolean checkSpecificRules() {
        var selectedDiagram = model.selectedDiagramProperty().get();
        if (selectedDiagram.getVariableByName(viewModel.getName()) != null) {
            DialogMessages.showError("Variable with the same name already exists!");
            return false;
        }

        return true;
    }

    @Override
    protected void copyToOriginal() {
        viewModelCopyFactory.copyTo(original, viewModel);
    }

}
