package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementType;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.OutputOptionViewModel;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main model class of the application. Holds (indirectly) all the information displayed in the application.
 */
public class Model {

    private final ObservableList<ProjectViewModel> projects;
    private final ObjectProperty<DiagramViewModel> selectedDiagram;

    private final Clipboard clipboard;
    private final ObjectProperty<CursorMode> cursorMode;
    private final ObjectProperty<GraphElementType> createElementType;

    private final StringProperty pathSPNP;
    private final StringProperty pathSPNPExamples;
    private final StringProperty pathPlotsLibrary;

    private final List<OutputOptionViewModel> outputOptions;

    private final ExecutorService executorService;
    private final ObjectProperty<Task<Void>> runningSimulationTask;
    private final Timer timer;

    public Model() {
        projects = FXCollections.observableArrayList();
        selectedDiagram = new SimpleObjectProperty<>();

        clipboard = new Clipboard();
        cursorMode = new SimpleObjectProperty<>(CursorMode.VIEW);
        createElementType = new SimpleObjectProperty<>(GraphElementType.PLACE);

        pathSPNP = new SimpleStringProperty("C:\\Spnp-Gui\\spnp");
        pathSPNPExamples = new SimpleStringProperty("C:\\Spnp-Gui\\spnp\\example");
        pathPlotsLibrary = new SimpleStringProperty("C:\\Spnp-Gui\\GRAPH_examples");

        outputOptions = new ArrayList<>();

        executorService = Executors.newSingleThreadExecutor();
        runningSimulationTask = new SimpleObjectProperty<>();
        timer = new Timer();
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public List<ElementViewModel> getClipboardElements() {
        return clipboard.getElements();
    }

    public List<FunctionViewModel> getClipboardFunctions() {
        return clipboard.getFunctions();
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

    public List<OutputOptionViewModel> getOutputOptions() {
        return outputOptions;
    }

    public Task<Void> getRunningSimulationTask() {
        return runningSimulationTask.get();
    }

    public ObjectProperty<Task<Void>> runningSimulationTaskProperty() {
        return runningSimulationTask;
    }

    public void runSimulationTask(Task<Void> task) {
        runningSimulationTask.set(task);
        executorService.execute(task);
    }

    public void stopRunningSimulationTask() {
        if (getRunningSimulationTask() != null) {
            getRunningSimulationTask().cancel(true);
            runningSimulationTask.set(null);
        }
    }

    public void cleanUp() {
        executorService.shutdownNow();
        timer.cancel();
    }

    public void clearSimulationRunningTask() {
        runningSimulationTask.set(null);
    }

    public Timer getTimer() {
        return timer;
    }

}
