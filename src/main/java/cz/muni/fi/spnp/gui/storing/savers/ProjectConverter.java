package cz.muni.fi.spnp.gui.storing.savers;

import cz.muni.fi.spnp.gui.storing.oldmodels.ProjectOldFormat;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

import java.util.stream.Collectors;

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
