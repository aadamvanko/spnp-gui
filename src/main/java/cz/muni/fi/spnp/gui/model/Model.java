package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model {
    private final Notifications notifications;
    private final ObservableList<ProjectViewModel> projects = FXCollections.observableArrayList();
    private final ObjectProperty<DiagramViewModel> selectedDiagram;

    public Model(Notifications notifications) {
        this.notifications = notifications;
        selectedDiagram = new SimpleObjectProperty<>();
    }

    public void addProject(ProjectViewModel projectViewModel) {
        projects.add(projectViewModel);
        notifications.newProjectAdded(projectViewModel);
    }

    public ObjectProperty<DiagramViewModel> selectedDiagramProperty() {
        return selectedDiagram;
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
