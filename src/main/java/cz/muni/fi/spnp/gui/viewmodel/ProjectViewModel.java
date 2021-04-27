package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        notifications.diagramRemoved(diagramViewModel);
    }

    public ObservableList<DiagramViewModel> getDiagrams() {
        return diagrams;
    }

    public boolean diagramExists(String name) {
        return diagrams.stream().anyMatch(diagramViewModel -> diagramViewModel.nameProperty().get().equals(name));
    }
//    private Map<String, FunctionViewModel> functions;
}
