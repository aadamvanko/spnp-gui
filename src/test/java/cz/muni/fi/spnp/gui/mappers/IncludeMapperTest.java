package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.gui.components.menu.view.includes.IncludeViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncludeMapperTest {

    private IncludeMapper includeMapper;

    @BeforeEach
    public void beforeEach() {
        includeMapper = new IncludeMapper();
    }

    @Test
    public void map_allFieldsValid() {
        var includeViewModel = new IncludeViewModel("<stdio.h>");

        var include = includeMapper.map(includeViewModel);

        assertEquals(include.getPath(), includeViewModel.getPath());
    }

}
