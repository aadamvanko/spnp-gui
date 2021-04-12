package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectViewModel {
    private final Map<String, DiagramViewModel> diagrams = new HashMap<>();
    private final Notifications notifications;
    private final StringProperty name;

    public ProjectViewModel(Notifications notifications, String name) {
        this.notifications = notifications;
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void addDiagram(DiagramViewModel diagramViewModel) {
        diagrams.put(diagramViewModel.nameProperty().get(), diagramViewModel);
        notifications.newDiagramAdded(diagramViewModel);
    }

    public void renameDiagram(String oldName, String newName) {
        DiagramViewModel diagramViewModel = diagrams.get(oldName);
        diagrams.remove(oldName);
        diagrams.put(newName, diagramViewModel);
    }

    public void removeDiagram(String diagramName) {

        DiagramViewModel diagramViewModel = diagrams.remove(diagramName);
//        notifications.diagramRemoved(this, diagramViewModel);
    }

    public Collection<DiagramViewModel> getDiagrams() {
        return diagrams.values();
    }

    public boolean diagramExists(String name) {
        return diagrams.containsKey(name);
    }
//    private Map<String, FunctionViewModel> functions;
}
