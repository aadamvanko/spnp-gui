package cz.muni.fi.spnp.gui.storage.oldformat;

import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.storage.oldformat.converters.OldProjectConverter;
import cz.muni.fi.spnp.gui.storage.oldformat.models.ProjectOldFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldProjectConverterTest {

    private OldProjectConverter oldProjectConverter;

    @BeforeEach
    public void beforeEach() {
        oldProjectConverter = new OldProjectConverter();
    }

    private ProjectOldFormat prepareBasicOldProject() {
        var oldProject = new ProjectOldFormat();
        oldProject.modelName = "oldProject1";
        oldProject.owner = "owner";
        oldProject.dateCreated = "Mon Aug 23 10:16:44 CEST 2021";
        oldProject.comment = "comment";
        return oldProject;
    }

    @Test
    public void convert_allFieldsValid() {
        var oldProject = prepareBasicOldProject();

        var projectOldFormat = oldProjectConverter.convert(oldProject);

        assertEqualsProjectFields(projectOldFormat, oldProject);
    }

    private void assertEqualsProjectFields(ProjectViewModel projectViewModel, ProjectOldFormat projectOldFormat) {
        assertEquals(projectOldFormat.modelName, projectViewModel.getName());
        assertEquals(projectOldFormat.owner, projectViewModel.getOwner());
        assertEquals(projectOldFormat.dateCreated, projectViewModel.getDateCreated());
        assertEquals(projectOldFormat.comment, projectViewModel.getComment());
    }

}
