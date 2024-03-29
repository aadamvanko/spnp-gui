package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.util.StringConverter;

/**
 * Converts the function view model to string representation and vice versa.
 */
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
