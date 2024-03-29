package cz.muni.fi.spnp.gui.components.menu.analysis.simulation;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;
import javafx.util.StringConverter;

/**
 * Converts the variable view model to the string representation and vice versa.
 */
public class VariableViewModelStringConverter extends StringConverter<VariableViewModel> {

    private final DiagramViewModel diagramViewModel;

    public VariableViewModelStringConverter(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    @Override
    public String toString(VariableViewModel variableViewModel) {
        if (variableViewModel == null) {
            return null;
        }
        return variableViewModel.getName();
    }

    @Override
    public VariableViewModel fromString(String name) {
        return diagramViewModel.getVariableByName(name);
    }

}
