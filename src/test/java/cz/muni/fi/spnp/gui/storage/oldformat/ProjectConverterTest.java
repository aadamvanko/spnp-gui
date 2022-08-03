package cz.muni.fi.spnp.gui.storage.oldformat;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.storage.oldformat.converters.ProjectConverter;
import cz.muni.fi.spnp.gui.storage.oldformat.models.ProjectOldFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectConverterTest {

    private ProjectConverter projectConverter;

    @BeforeEach
    public void beforeEach() {
        projectConverter = new ProjectConverter();
    }

    private ProjectViewModel prepareBasicProjectViewModel() {
        var projectViewModel = new ProjectViewModel();
        projectViewModel.nameProperty().set("project1");
        projectViewModel.setOwner("owner");
        projectViewModel.setDateCreated("Mon Aug 23 10:16:44 CEST 2021");
        projectViewModel.setComment("comment");
        return projectViewModel;
    }

    @Test
    public void convert_allFieldsValidWithNoDiagrams() {
        var projectViewModel = prepareBasicProjectViewModel();

        var projectOldFormat = projectConverter.convert(projectViewModel);

        assertEqualsProjectFields(projectOldFormat, projectViewModel);
        assertEquals(projectOldFormat.submodelsNames, Collections.emptyList());
    }

    @Test
    public void convert_allFieldsValidWith2Diagrams() {
        var projectViewModel = prepareBasicProjectViewModel();

        DiagramViewModel diagramViewModel1 = new DiagramViewModel(projectViewModel);
        diagramViewModel1.nameProperty().set("diagram1");
        projectViewModel.getDiagrams().add(diagramViewModel1);

        DiagramViewModel diagramViewModel2 = new DiagramViewModel(projectViewModel);
        diagramViewModel2.nameProperty().set("diagram2");
        projectViewModel.getDiagrams().add(diagramViewModel2);

        var projectOldFormat = projectConverter.convert(projectViewModel);

        assertEqualsProjectFields(projectOldFormat, projectViewModel);
        assertEquals(projectOldFormat.submodelsNames, List.of("diagram1", "diagram2"));
    }

    private void assertEqualsProjectFields(ProjectOldFormat projectOldFormat, ProjectViewModel projectViewModel) {
        assertEquals(projectOldFormat.modelName, projectViewModel.getName());
        assertEquals(projectOldFormat.owner, projectViewModel.getOwner());
        assertEquals(projectOldFormat.dateCreated, projectViewModel.getDateCreated());
        assertEquals(projectOldFormat.comment, projectViewModel.getComment());
    }

}
