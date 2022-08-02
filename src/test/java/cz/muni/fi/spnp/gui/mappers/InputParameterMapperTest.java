package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.parameters.DoubleInputParameter;
import cz.muni.fi.spnp.core.transformators.spnp.parameters.InputParameter;
import cz.muni.fi.spnp.core.transformators.spnp.parameters.IntegerInputParameter;
import cz.muni.fi.spnp.gui.components.menu.view.inputparameters.InputParameterViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableDataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputParameterMapperTest {

    private InputParameterMapper inputParameterMapper;

    @BeforeEach
    public void beforeEach() {
        inputParameterMapper = new InputParameterMapper();
    }

    @Test
    public void map_allFieldsValidInt() {
        var inputParameterViewModel = new InputParameterViewModel("inputParameterInt", VariableDataType.INT, "promptText");

        var inputParameter = inputParameterMapper.map(inputParameterViewModel);

        assertEqualsInputParameter(inputParameterViewModel, inputParameter);
        assertTrue(inputParameter instanceof IntegerInputParameter);
    }

    @Test
    public void map_allFieldsValidDouble() {
        var inputParameterViewModel = new InputParameterViewModel("inputParameterDouble", VariableDataType.DOUBLE, "promptText");

        var inputParameter = inputParameterMapper.map(inputParameterViewModel);

        assertEqualsInputParameter(inputParameterViewModel, inputParameter);
        assertTrue(inputParameter instanceof DoubleInputParameter);
    }

    private void assertEqualsInputParameter(InputParameterViewModel inputParameterViewModel, InputParameter inputParameter) {
        assertEquals(inputParameter.getParameterName(), inputParameterViewModel.getName());
        assertEquals(inputParameter.getUserPromptText(), inputParameterViewModel.getUserPromptText());
    }

}
