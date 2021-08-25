package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.VariableDataType;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;

public class VariableView extends GeneralItemView<VariableViewModel> {

    public VariableView(VariableViewModel variableViewModel, ItemViewMode itemViewMode) {
        super(variableViewModel, itemViewMode);

        addRowTextField("Name:", variableViewModel.nameProperty());
        addRowEnumChoiceBox("Kind:", variableViewModel.kindProperty(), VariableType.class);
        addRowEnumChoiceBox("Type:", variableViewModel.typeProperty(), VariableDataType.class);
        addRowTextField("Value:", variableViewModel.valueProperty());
    }
}
