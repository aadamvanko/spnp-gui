package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.InputParameterViewModel;
import cz.muni.fi.spnp.gui.viewmodel.VariableDataType;

public class InputParameterView extends GeneralItemView<InputParameterViewModel> {

    public InputParameterView(InputParameterViewModel inputParameterViewModel, ItemViewMode itemViewMode) {
        super(inputParameterViewModel, itemViewMode);

        addRowText("Name:", inputParameterViewModel.nameProperty());
        addRowEnum("Type:", inputParameterViewModel.typeProperty(), VariableDataType.class);
        addRowText("User prompt text:", inputParameterViewModel.userPromptTextProperty());
    }

}
