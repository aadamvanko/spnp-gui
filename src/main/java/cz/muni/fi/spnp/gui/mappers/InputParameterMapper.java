package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.parameters.DoubleInputParameter;
import cz.muni.fi.spnp.core.transformators.spnp.parameters.InputParameter;
import cz.muni.fi.spnp.core.transformators.spnp.parameters.IntegerInputParameter;
import cz.muni.fi.spnp.gui.components.menu.view.inputparameters.InputParameterViewModel;

public class InputParameterMapper {

    public InputParameter map(InputParameterViewModel inputParameterViewModel) {
        switch (inputParameterViewModel.getType()) {
            case INT:
                return new IntegerInputParameter(
                        inputParameterViewModel.getName(),
                        inputParameterViewModel.getUserPromptText()
                );

            case DOUBLE:
                return new DoubleInputParameter(
                        inputParameterViewModel.getName(),
                        inputParameterViewModel.getUserPromptText()
                );
        }
        throw new AssertionError("Unsupported input parameter data type " + inputParameterViewModel.getType());
    }

}
