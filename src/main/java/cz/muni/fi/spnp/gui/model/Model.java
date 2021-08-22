package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final ObservableList<ProjectViewModel> projects;
    private final ObjectProperty<DiagramViewModel> selectedDiagram;

    private final List<ElementViewModel> clipboardElements;
    private ClipboardOperationType clipboardOperationType;
    private final ObjectProperty<CursorMode> cursorMode;
    private final ObjectProperty<GraphElementType> createElementType;

    private final StringProperty pathSPNP;
    private final StringProperty pathSPNPExamples;
    private final StringProperty pathPlotsLibrary;

    public Model() {
        projects = FXCollections.observableArrayList();
        selectedDiagram = new SimpleObjectProperty<>();
        cursorMode = new SimpleObjectProperty<>(CursorMode.VIEW);
        createElementType = new SimpleObjectProperty<>(GraphElementType.PLACE);
        clipboardElements = new ArrayList<>();

        pathSPNP = new SimpleStringProperty("C:\\Spnp-Gui");
        pathSPNPExamples = new SimpleStringProperty("C:\\Spnp-Gui\\Examples-Official");
        pathPlotsLibrary = new SimpleStringProperty("C:\\Spnp-Gui\\GRAPH_examples");
    }

    public ClipboardOperationType getClipboardOperationType() {
        return clipboardOperationType;
    }

    public void setClipboardOperationType(ClipboardOperationType clipboardOperationType) {
        this.clipboardOperationType = clipboardOperationType;
    }

    public List<ElementViewModel> getClipboardElements() {
        return clipboardElements;
    }

    public CursorMode getCursorMode() {
        return cursorMode.get();
    }

    public ObjectProperty<CursorMode> cursorModeProperty() {
        return cursorMode;
    }

    public GraphElementType getCreateElementType() {
        return createElementType.get();
    }

    public ObjectProperty<GraphElementType> createElementTypeProperty() {
        return createElementType;
    }

    public ObjectProperty<DiagramViewModel> selectedDiagramProperty() {
        return selectedDiagram;
    }

    public String getPathSPNP() {
        return pathSPNP.get();
    }

    public StringProperty pathSPNPProperty() {
        return pathSPNP;
    }

    public String getPathSPNPExamples() {
        return pathSPNPExamples.get();
    }

    public StringProperty pathSPNPExamplesProperty() {
        return pathSPNPExamples;
    }

    public String getPathPlotsLibrary() {
        return pathPlotsLibrary.get();
    }

    public StringProperty pathPlotsLibraryProperty() {
        return pathPlotsLibrary;
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
