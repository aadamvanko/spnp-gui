package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final Notifications notifications;

    private final List<ProjectViewModel> projects = new ArrayList<>();

    private final ProjectViewModel selectedProject;
    private DiagramViewModel selectedDiagram;

    public Model(Notifications notifications) {
        this.notifications = notifications;

        ProjectViewModel project = new ProjectViewModel("Project 1");
        projects.add(project);
        selectedProject = project;
    }

    public void addProject(ProjectViewModel projectViewModel) {
        projects.add(projectViewModel);
//        notifications.newProjectAdded(projectViewModel);
    }

    public void selectProject(String projectName) {

    }

    public void selectDiagram(DiagramViewModel diagramViewModel) {
        selectedDiagram = diagramViewModel;
        notifications.selectedDiagramChanged(selectedDiagram);
    }
}
