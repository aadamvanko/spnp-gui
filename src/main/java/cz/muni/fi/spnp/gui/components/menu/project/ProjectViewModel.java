package cz.muni.fi.spnp.gui.components.menu.project;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * View model of the project.
 */
public class ProjectViewModel extends DisplayableViewModel {
    private final ObservableList<DiagramViewModel> diagrams;
    private String owner;
    private String dateCreated;
    private String comment;

    public ProjectViewModel() {
        nameProperty().set("unnamedProject");
        this.diagrams = FXCollections.observableArrayList();
    }

    public ObservableList<DiagramViewModel> getDiagrams() {
        return diagrams;
    }

    public boolean diagramExists(String name) {
        return diagrams.stream().anyMatch(diagramViewModel -> diagramViewModel.nameProperty().get().equals(name));
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
