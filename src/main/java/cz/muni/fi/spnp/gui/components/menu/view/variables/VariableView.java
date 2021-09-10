package cz.muni.fi.spnp.gui.components.menu.view.variables;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.view.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.VariableDataType;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;

public class VariableView extends GeneralItemView<VariableViewModel> {

    public VariableView(Model model, VariableViewModel variableViewModel, VariableViewModel original, ItemViewMode itemViewMode) {
        super(model, variableViewModel, original, itemViewMode);

        addRowTextField("Name:", variableViewModel.nameProperty());
        addRowEnumChoiceBox("Kind:", variableViewModel.kindProperty(), VariableType.class);
        addRowEnumChoiceBox("Type:", variableViewModel.typeProperty(), VariableDataType.class);
        addRowTextField("Value:", variableViewModel.valueProperty());
    }

    @Override
    protected boolean checkSpecificRules() {
        var selectedDiagram = model.selectedDiagramProperty().get();
        if (selectedDiagram.getInputParameterByName(viewModel.getName()) != null) {
            DialogMessages.showError("Input parameter with the same name already exists!");
            return false;
        }

        return true;
    }

    @Override
    protected void copyToOriginal() {
        viewModelCopyFactory.copyTo(original, viewModel);
    }

}
