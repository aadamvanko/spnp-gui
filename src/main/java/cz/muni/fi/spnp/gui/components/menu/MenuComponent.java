package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.DiagramComponent;
import cz.muni.fi.spnp.gui.components.graph.operations.*;
import cz.muni.fi.spnp.gui.components.menu.analysis.PreferencesView;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsView;
import cz.muni.fi.spnp.gui.components.menu.diagram.NewDiagramView;
import cz.muni.fi.spnp.gui.components.menu.help.AboutWindow;
import cz.muni.fi.spnp.gui.components.menu.project.NewProjectView;
import cz.muni.fi.spnp.gui.components.menu.view.defines.DefinesTableViewWindow;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionsView;
import cz.muni.fi.spnp.gui.components.menu.view.includes.IncludesTableViewWindow;
import cz.muni.fi.spnp.gui.components.menu.view.variables.InputParametersTableViewWindow;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariablesTableViewWindow;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.storing.loaders.OldFileLoader;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

public class MenuComponent extends ApplicationComponent {

    private final MenuBar menuBar;
    private final DefinesTableViewWindow definesWindowView;
    private final MenuItem menuItemViewIncludes;
    private final IncludesTableViewWindow includesTableViewWindow;
    private final MenuItem menuItemViewDefines;
    private final VariablesTableViewWindow variablesWindowView;
    private final MenuItem menuItemViewVariables;
    private final InputParametersTableViewWindow inputParametersTableViewWindow;
    private final MenuItem menuItemViewInputParameters;
    private final FunctionsView functionsView;
    private final MenuItem menuItemViewFunctions;
    private final NewProjectView newProjectView;
    private final MenuItem menuItemNewProject;
    private final NewDiagramView newDiagramView;
    private final MenuItem menuItemNewDiagram;
    private final DiagramComponent diagramComponent;

    public MenuComponent(Model model, DiagramComponent diagramComponent) {
        super(model);

        this.diagramComponent = diagramComponent;

        menuBar = new MenuBar();

        Menu menuProject = new Menu("_Project");

        var menuItemOpenProject = new MenuItem("_Open project");
        menuItemOpenProject.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuItemOpenProject.setOnAction(this::onOpenProjectClickedHandler);
        menuProject.getItems().add(menuItemOpenProject);

        newProjectView = new NewProjectView(model);
        menuItemNewProject = new MenuItem("_New Project");
        menuItemNewProject.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        menuItemNewProject.setOnAction(actionEvent -> newProjectView.getStage().showAndWait());
        menuProject.getItems().add(menuItemNewProject);

        var menuItemSaveProject = new MenuItem("_Save project");
        menuItemSaveProject.setOnAction(this::onSaveProjectClickedHandler);
        menuItemSaveProject.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        menuItemSaveProject.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuProject.getItems().add(menuItemSaveProject);

        var menuItemCloseProject = new MenuItem("_Close project");
        menuItemCloseProject.setOnAction(this::onCloseProjectClickedHandler);
        menuItemCloseProject.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuProject.getItems().add(menuItemCloseProject);
        menuBar.getMenus().add(menuProject);

        var menuDiagram = new Menu("_Diagram");

        newDiagramView = new NewDiagramView(model);
        menuItemNewDiagram = new MenuItem("_New Diagram");
        menuItemNewDiagram.setDisable(true);
        menuItemNewDiagram.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        menuItemNewDiagram.setOnAction(actionEvent -> newDiagramView.getStage().showAndWait());
        menuDiagram.getItems().add(menuItemNewDiagram);
        menuBar.getMenus().add(menuDiagram);

        Menu menuEdit = new Menu("_Edit");
        var menuItemSelectAll = new MenuItem("_Select all");
        menuItemSelectAll.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        menuItemSelectAll.setOnAction(actionEvent -> new OperationSelectAll(model, model.selectedDiagramProperty().get()).execute());
        var menuItemPaste = new MenuItem("_Paste");
        menuItemPaste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        menuItemPaste.setOnAction(actionEvent -> new OperationPasteElements(model, model.selectedDiagramProperty().get()).execute());
        var menuItemCopy = new MenuItem("_Copy");
        menuItemCopy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        menuItemCopy.setOnAction(actionEvent -> new OperationCopyElements(model, model.selectedDiagramProperty().get()).execute());
        var menuItemCut = new MenuItem("C_ut");
        menuItemCut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        menuItemCut.setOnAction(actionEvent -> new OperationCutElements(model, model.selectedDiagramProperty().get()).execute());
        var menuItemDelete = new MenuItem("_Delete");
        menuItemDelete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        menuItemDelete.setOnAction(actionEvent -> new OperationDeleteElements(model, model.selectedDiagramProperty().get()).execute());
        menuEdit.getItems().addAll(menuItemSelectAll, menuItemPaste, menuItemCopy, menuItemCut, menuItemDelete);
        menuBar.getMenus().add(menuEdit);

        Menu menuView = new Menu("_View");

        includesTableViewWindow = new IncludesTableViewWindow(model);
        menuItemViewIncludes = new MenuItem("_Includes");
        menuItemViewIncludes.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        menuItemViewIncludes.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewIncludes.setOnAction(actionEvent -> includesTableViewWindow.getStage().showAndWait());
        menuView.getItems().add(menuItemViewIncludes);

        definesWindowView = new DefinesTableViewWindow(model);
        menuItemViewDefines = new MenuItem("_Defines");
        menuItemViewDefines.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        menuItemViewDefines.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewDefines.setOnAction(actionEvent -> definesWindowView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewDefines);

        variablesWindowView = new VariablesTableViewWindow(model);
        menuItemViewVariables = new MenuItem("Varia_bles");
        menuItemViewVariables.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        menuItemViewVariables.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewVariables.setOnAction(actionEvent -> variablesWindowView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewVariables);

        inputParametersTableViewWindow = new InputParametersTableViewWindow(model);
        menuItemViewInputParameters = new MenuItem("Input _Parameters");
        menuItemViewInputParameters.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        menuItemViewInputParameters.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewInputParameters.setOnAction(actionEvent -> inputParametersTableViewWindow.getStage().showAndWait());
//        menuView.getItems().add(menuItemViewInputParameters);

        functionsView = new FunctionsView();
        menuItemViewFunctions = new MenuItem("_Functions");
        menuItemViewFunctions.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        menuItemViewFunctions.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewFunctions.setOnAction(actionEvent -> functionsView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewFunctions);
        menuBar.getMenus().add(menuView);

        var menuSimulation = new Menu("_Analysis");

        var preferencesView = new PreferencesView(model);
        var menuItemPreferences = new MenuItem("_Preferences");
        menuItemPreferences.setOnAction(actionEvent -> preferencesView.getStage().showAndWait());
        menuSimulation.getItems().add(menuItemPreferences);

        var menuItemRun = new MenuItem("_Run");
        menuItemRun.setOnAction(actionEvent -> new OutputOptionsView(model, model.selectedDiagramProperty().get()).getStage().showAndWait());
        menuItemRun.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        menuItemRun.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuSimulation.getItems().add(menuItemRun);

        var menuItemStop = new MenuItem("_Stop");
        menuItemStop.setOnAction(actionEvent -> {
            System.out.println("Stopping simulation/analysis...");
            model.stopRunningSimulationTask();
        });
        menuItemStop.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuItemStop.disableProperty().bind(model.runningSimulationTaskProperty().isNull());
        menuSimulation.getItems().add(menuItemStop);
        menuBar.getMenus().add(menuSimulation);

        var menuExport = new Menu("E_xport");
        var menuItemScreenshotDiagram = new MenuItem("_Diagram Screenshot");
        menuItemScreenshotDiagram.setOnAction(this::onScreenshotDiagramHandler);
        menuItemScreenshotDiagram.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuExport.getItems().add(menuItemScreenshotDiagram);
        menuBar.getMenus().add(menuExport);

        Menu menuHelp = new Menu("_Help");
        var aboutWindow = new AboutWindow();
        var menuItemAbout = new MenuItem("_About");
        menuItemAbout.setOnAction(actionEvent -> aboutWindow.getStage().showAndWait());
        menuHelp.getItems().add(menuItemAbout);
        menuBar.getMenus().add(menuHelp);

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
        menuItemNewDiagram.disableProperty().bind(Bindings.size(model.getProjects()).isEqualTo(0));
    }

    private void onScreenshotDiagramHandler(ActionEvent actionEvent) {
        var fileChooser = new FileChooser();
        var selectedDiagram = model.selectedDiagramProperty().get();
        fileChooser.setInitialFileName(String.format("%s_%s.png", selectedDiagram.getProject().getName(), selectedDiagram.getName()));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png"));
        fileChooser.setTitle("Save screenshot of diagram " + model.selectedDiagramProperty().get().getName());
        var file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());
        diagramComponent.saveScreenshotCurrentDiagram(file);
    }

    private void onOpenProjectClickedHandler(ActionEvent actionEvent) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select project file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Project files", "*.rgl"));
        var file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            var oldFileLoader = new OldFileLoader();
            var loadedProject = oldFileLoader.loadProject(file.getAbsolutePath());
            if (loadedProject != null) {
                model.getProjects().add(loadedProject);
            }
        }
    }

    private void onSaveProjectClickedHandler(ActionEvent actionEvent) {
        var project = model.selectedDiagramProperty().get().getProject();
        var projectSaver = new ProjectSaver(menuBar.getScene().getWindow(), project);
        projectSaver.save();
    }

    private void onCloseProjectClickedHandler(ActionEvent actionEvent) {
        var project = model.selectedDiagramProperty().get().getProject();
        model.getProjects().remove(project);
    }

    @Override
    public Node getRoot() {
        return menuBar;
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (newDiagram == null) {
            includesTableViewWindow.bindSourceCollection(FXCollections.emptyObservableList());
            definesWindowView.bindSourceCollection(FXCollections.emptyObservableList());
            variablesWindowView.bindSourceCollection(FXCollections.emptyObservableList());
            inputParametersTableViewWindow.bindSourceCollection(FXCollections.emptyObservableList());
            functionsView.unbindDiagramViewModel();
            return;
        }

        includesTableViewWindow.bindSourceCollection(newDiagram.getIncludes());
        definesWindowView.bindSourceCollection(newDiagram.getDefines());
        variablesWindowView.bindSourceCollection(newDiagram.getVariables());
        inputParametersTableViewWindow.bindSourceCollection(newDiagram.getInputParameters());
        functionsView.bindDiagramViewModel(newDiagram);
    }

}
