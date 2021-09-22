package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.AnalysisOptionsViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.SimulationOptionsViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.OutputOptionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final SimulationOptionsViewModel simulationOptions;
    private final AnalysisOptionsViewModel analysisOptions;

    private final ExecutorService executorService;
    private final ObjectProperty<Task<Void>> runningSimulationTask;

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
        simulationOptions = new SimulationOptionsViewModel();
        analysisOptions = new AnalysisOptionsViewModel();

        executorService = Executors.newSingleThreadExecutor();
        runningSimulationTask = new SimpleObjectProperty<>();
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

    public List<OutputOptionViewModel> getOutputOptions() {
        return outputOptions;
    }

    public SimulationOptionsViewModel getSimulationOptions() {
        return simulationOptions;
    }

    public AnalysisOptionsViewModel getAnalysisOptions() {
        return analysisOptions;
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
        getRunningSimulationTask().cancel(true);
        runningSimulationTask.set(null);
    }

    public void cleanUp() {
        executorService.shutdownNow();
    }

}
