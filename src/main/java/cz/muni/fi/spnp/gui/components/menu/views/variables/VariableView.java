package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.VariableDataType;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;

public class VariableView extends GeneralItemView<VariableViewModel> {

    public VariableView(VariableViewModel variableViewModel, ItemViewMode itemViewMode) {
        super(variableViewModel, itemViewMode);

        addRowText("Name:", variableViewModel.nameProperty());
        addRowEnum("Kind:", variableViewModel.kindProperty(), VariableType.class);
        addRowEnum("Type:", variableViewModel.typeProperty(), VariableDataType.class);
        addRowText("Value:", variableViewModel.valueProperty());
    }
}
