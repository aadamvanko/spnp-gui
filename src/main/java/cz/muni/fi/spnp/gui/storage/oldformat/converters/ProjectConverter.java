package cz.muni.fi.spnp.gui.storage.oldformat.converters;

import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.storage.oldformat.models.ProjectOldFormat;

import java.util.stream.Collectors;

/**
 * Converts the project view model to the old project.
 */
public class ProjectConverter {

    public ProjectOldFormat convert(ProjectViewModel project) {
        var oldProject = new ProjectOldFormat();
        oldProject.modelName = project.nameProperty().get();
        oldProject.owner = project.getOwner();
        oldProject.dateCreated = project.getDateCreated();
        oldProject.comment = project.getComment();
        oldProject.submodelsNames = project.getDiagrams().stream().map(d -> d.nameProperty().get()).collect(Collectors.toList());
        return oldProject;
    }

}
