package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.variables.DoubleVariable;
import cz.muni.fi.spnp.core.transformators.spnp.variables.IntegerVariable;
import cz.muni.fi.spnp.core.transformators.spnp.variables.Variable;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;

/**
 * Maps the variables to the general model variables.
 */
public class VariableMapper {

    public Variable map(VariableViewModel variableViewModel) {
        switch (variableViewModel.getType()) {
            case INT:
                return new IntegerVariable(
                        variableViewModel.getName(),
                        variableViewModel.getKind(),
                        Integer.parseInt(variableViewModel.getValue())
                );

            case DOUBLE:
                return new DoubleVariable(
                        variableViewModel.getName(),
                        variableViewModel.getKind(),
                        Double.parseDouble(variableViewModel.getValue())
                );
        }
        throw new AssertionError("Unsupported variable data type " + variableViewModel.getType());
    }

}
