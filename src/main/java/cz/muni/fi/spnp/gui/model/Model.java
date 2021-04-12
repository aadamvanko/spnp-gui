package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final Notifications notifications;
    private final ObservableList<ProjectViewModel> projects = FXCollections.observableArrayList();
    private final ProjectViewModel selectedProject;
    private DiagramViewModel selectedDiagram;

    public Model(Notifications notifications) {
        this.notifications = notifications;
        selectedProject = null;
//        ProjectViewModel project = new ProjectViewModel("Project 1");
//        projects.add(project);
//        selectedProject = project;
    }

    public void addProject(ProjectViewModel projectViewModel) {
        projects.add(projectViewModel);
        notifications.newProjectAdded(projectViewModel);
    }

    public void selectProject(String projectName) {

    }

    public void selectDiagram(DiagramViewModel diagramViewModel) {
        selectedDiagram = diagramViewModel;
        notifications.selectedDiagramChanged(selectedDiagram);
    }

    public ProjectViewModel getSelectedProject() {
        return selectedProject;
    }

    public boolean projectExists(String name) {
        var found = projects.stream().filter(project -> project.nameProperty().get().equals(name));
        return found.count() > 0;
    }

    public ObservableList<ProjectViewModel> getProjects() {
        return projects;
    }

    public ProjectViewModel getProject(String name) {
        for (var project : projects) {
            if (project.nameProperty().get().equals(name)) {
                return project;
            }
        }
        return null;
    }
}
