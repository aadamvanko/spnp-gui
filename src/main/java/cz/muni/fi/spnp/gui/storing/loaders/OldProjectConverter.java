package cz.muni.fi.spnp.gui.storing.loaders;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.storing.oldmodels.ProjectOldFormat;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

public class OldProjectConverter {

    private final Notifications notifications;

    public OldProjectConverter(Notifications notifications) {
        this.notifications = notifications;
    }

    public ProjectViewModel convert(ProjectOldFormat oldProject) {
        var project = new ProjectViewModel();
        project.nameProperty().set(oldProject.modelName);
        project.setOwner(oldProject.owner);
        project.setDateCreated(oldProject.dateCreated);
        project.setComment(oldProject.comment);
        return project;
    }

}
