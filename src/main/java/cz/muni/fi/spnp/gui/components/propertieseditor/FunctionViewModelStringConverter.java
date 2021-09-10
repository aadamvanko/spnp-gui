package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.util.StringConverter;

public class FunctionViewModelStringConverter extends StringConverter<FunctionViewModel> {

    private final DiagramViewModel diagramViewModel;

    public FunctionViewModelStringConverter(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    @Override
    public String toString(FunctionViewModel functionViewModel) {
        if (functionViewModel == null) {
            return null;
        }
        return functionViewModel.getName();
    }

    @Override
    public FunctionViewModel fromString(String name) {
        return diagramViewModel.getFunctionByName(name);
    }

}
