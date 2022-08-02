package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.variables.DoubleVariable;
import cz.muni.fi.spnp.core.transformators.spnp.variables.IntegerVariable;
import cz.muni.fi.spnp.core.transformators.spnp.variables.Variable;
import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableDataType;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VariableMapperTest {

    private VariableMapper variableMapper;

    @BeforeEach
    public void beforeEach() {
        variableMapper = new VariableMapper();
    }

    @Test
    public void map_allFieldsValidGlobalInt() {
        VariableViewModel variableViewModel = new VariableViewModel("intVar", VariableType.Global, VariableDataType.INT, "10");

        var intVariable = variableMapper.map(variableViewModel);

        assertEqualsVariable(variableViewModel, intVariable);
        assertTrue(intVariable instanceof IntegerVariable);
    }

    @Test
    public void map_allFieldsValidGlobalDouble() {
        VariableViewModel variableViewModel = new VariableViewModel("doubleVar", VariableType.Global, VariableDataType.DOUBLE, "1.23");

        var doubleVariable = variableMapper.map(variableViewModel);

        assertEqualsVariable(variableViewModel, doubleVariable);
        assertTrue(doubleVariable instanceof DoubleVariable);
    }

    @Test
    public void map_allFieldsValidParameterInt() {
        VariableViewModel variableViewModel = new VariableViewModel("intVar", VariableType.Parameter, VariableDataType.INT, "10");

        var intVariable = variableMapper.map(variableViewModel);

        assertEqualsVariable(variableViewModel, intVariable);
        assertTrue(intVariable instanceof IntegerVariable);
    }

    @Test
    public void map_allFieldsValidParameterDouble() {
        VariableViewModel variableViewModel = new VariableViewModel("doubleVar", VariableType.Parameter, VariableDataType.DOUBLE, "1.23");

        var doubleVariable = variableMapper.map(variableViewModel);

        assertEqualsVariable(variableViewModel, doubleVariable);
        assertTrue(doubleVariable instanceof DoubleVariable);
    }

    private void assertEqualsVariable(VariableViewModel variableViewModel, Variable intVariable) {
        assertEquals(intVariable.getName(), variableViewModel.getName());
        assertEquals(intVariable.getType(), variableViewModel.getKind());
        // TODO make other fields accessible via getters in spnp.core?
    }

}
