package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectViewModel extends DisplayableViewModel {
    private final ObservableList<DiagramViewModel> diagrams;
    private final Notifications notifications;

    public ProjectViewModel(Notifications notifications, String name) {
        super(name);
        this.diagrams = FXCollections.observableArrayList();
        this.notifications = notifications;
    }

    public void addDiagram(DiagramViewModel diagramViewModel) {
        diagrams.add(diagramViewModel);
        notifications.newDiagramAdded(diagramViewModel);
    }

    public void removeDiagram(DiagramViewModel diagramViewModel) {
        diagrams.remove(diagramViewModel);
        notifications.diagramDeleted(diagramViewModel);
    }

    public ObservableList<DiagramViewModel> getDiagrams() {
        return diagrams;
    }

    public boolean diagramExists(String name) {
        return diagrams.stream().anyMatch(diagramViewModel -> diagramViewModel.nameProperty().get().equals(name));
    }
//    private Map<String, FunctionViewModel> functions;
}
