package cz.muni.fi.spnp.gui.components.menu.view.variables.views;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.common.DialogMessages;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableDataType;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;

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
