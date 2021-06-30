package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;

public class DefineView2 extends GeneralItemView<DefineViewModel> {

    public DefineView2(DefineViewModel defineViewModel, ItemViewMode itemViewMode) {
        super(defineViewModel, itemViewMode);

        addRowText("Name:", defineViewModel.nameProperty());
        addRowText("Expression:", defineViewModel.expressionProperty());

        buttonOk.setOnMouseClicked(mouseEvent -> {
            if (anyBlankValues()) {
                return;
            }

            if (itemViewMode == ItemViewMode.ADD) {
                if (diagramViewModel.getDefines().contains(viewModel)) {
                    DialogMessages.showError("Conflicting name!");
                    return;
                } else {
                    diagramViewModel.getDefines().add(viewModel);
                }
            }
            // TODO prevent editing name to same

            bindings.forEach(b -> b.destroy());
            stage.close();
        });

        buttonCancel.setOnMouseClicked(mouseEvent -> {
            diagramViewModel = null;
            bindings.forEach(b -> b.destroy());
            stage.close();
        });
    }
}
