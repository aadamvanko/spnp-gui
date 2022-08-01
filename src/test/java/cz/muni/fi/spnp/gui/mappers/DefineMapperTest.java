package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.gui.components.menu.view.defines.DefineViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefineMapperTest {

    private DefineMapper defineMapper;

    @BeforeEach
    public void beforeEach() {
        defineMapper = new DefineMapper();
    }

    @Test
    public void map_allFieldsValid() {
        DefineViewModel defineViewModel = new DefineViewModel("INT_NUMBER", "10");

        var define = defineMapper.map(defineViewModel);

        assertEquals(define.getName(), defineViewModel.getName());
        assertEquals(define.getExpression(), defineViewModel.getExpression());
    }

}
