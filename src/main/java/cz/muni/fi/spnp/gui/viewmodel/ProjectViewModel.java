package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectViewModel {
    private final Map<String, DiagramViewModel> diagrams = new HashMap<>();
    private final StringProperty name;

    public ProjectViewModel(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void addDiagram(DiagramViewModel diagramViewModel) {
        diagrams.put(diagramViewModel.nameProperty().get(), diagramViewModel);
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
//    private Map<String, FunctionViewModel> functions;
}
