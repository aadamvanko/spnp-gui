package cz.muni.fi.spnp.gui.storage.oldformat.converters;

import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.storage.oldformat.models.ProjectOldFormat;

/**
 * Converts the old project to the project view model.
 */
public class OldProjectConverter {

    public ProjectViewModel convert(ProjectOldFormat oldProject) {
        var project = new ProjectViewModel();
        project.nameProperty().set(oldProject.modelName);
        project.setOwner(oldProject.owner);
        project.setDateCreated(oldProject.dateCreated);
        project.setComment(oldProject.comment);
        return project;
    }

}
