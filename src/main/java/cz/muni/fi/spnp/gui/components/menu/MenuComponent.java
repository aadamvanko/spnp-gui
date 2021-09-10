package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.operations.*;
import cz.muni.fi.spnp.gui.components.menu.analysis.PreferencesView;
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
import cz.muni.fi.spnp.gui.storing.savers.OldFileSaver;
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
import javafx.stage.DirectoryChooser;
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

    public MenuComponent(Model model) {
        super(model);

        menuBar = new MenuBar();

        Menu menuProject = new Menu("_Project");

        var menuItemOpenProject = new MenuItem("Open project");
        menuItemOpenProject.setOnAction(this::onOpenProjectClickedHandler);
        menuProject.getItems().add(menuItemOpenProject);

        newProjectView = new NewProjectView(model);
        menuItemNewProject = new MenuItem("New Project");
        menuItemNewProject.setOnAction(actionEvent -> newProjectView.getStage().showAndWait());
        menuProject.getItems().add(menuItemNewProject);

        var menuItemSaveProject = new MenuItem("Save project");
        menuItemSaveProject.setOnAction(this::onSaveProjectClickedHandler);
        menuItemSaveProject.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuProject.getItems().add(menuItemSaveProject);

        var menuItemCloseProject = new MenuItem("Close project");
        menuItemCloseProject.setOnAction(this::onCloseProjectClickedHandler);
        menuItemCloseProject.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuProject.getItems().add(menuItemCloseProject);
        menuBar.getMenus().add(menuProject);

        var menuDiagram = new Menu("_Diagram");

        newDiagramView = new NewDiagramView(model);
        menuItemNewDiagram = new MenuItem("New Diagram");
        menuItemNewDiagram.setDisable(true);
        menuItemNewDiagram.setOnAction(actionEvent -> newDiagramView.getStage().showAndWait());
        menuDiagram.getItems().add(menuItemNewDiagram);
        menuBar.getMenus().add(menuDiagram);

        Menu menuEdit = new Menu("_Edit");
        var menuItemSelectAll = new MenuItem("Select all");
        menuItemSelectAll.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        menuItemSelectAll.setOnAction(actionEvent -> new OperationSelectAll(model, model.selectedDiagramProperty().get()).execute());
        var menuItemPaste = new MenuItem("Paste");
        menuItemPaste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        menuItemPaste.setOnAction(actionEvent -> new OperationPasteElements(model, model.selectedDiagramProperty().get()).execute());
        var menuItemCopy = new MenuItem("Copy");
        menuItemCopy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        menuItemCopy.setOnAction(actionEvent -> new OperationCopyElements(model, model.selectedDiagramProperty().get()).execute());
        var menuItemCut = new MenuItem("Cut");
        menuItemCut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        menuItemCut.setOnAction(actionEvent -> new OperationCutElements(model, model.selectedDiagramProperty().get()).execute());
        var menuItemDelete = new MenuItem("Delete");
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
        menuItemViewVariables = new MenuItem("Va_riables");
        menuItemViewVariables.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        menuItemViewVariables.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewVariables.setOnAction(actionEvent -> variablesWindowView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewVariables);

        inputParametersTableViewWindow = new InputParametersTableViewWindow(model);
        menuItemViewInputParameters = new MenuItem("Input _Parameters");
        menuItemViewInputParameters.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        menuItemViewInputParameters.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewInputParameters.setOnAction(actionEvent -> inputParametersTableViewWindow.getStage().showAndWait());
        menuView.getItems().add(menuItemViewInputParameters);

        functionsView = new FunctionsView();
        menuItemViewFunctions = new MenuItem("_Functions");
        menuItemViewFunctions.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        menuItemViewFunctions.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewFunctions.setOnAction(actionEvent -> functionsView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewFunctions);
        menuBar.getMenus().add(menuView);

        var menuSimulation = new Menu("_Analysis");

        var preferencesView = new PreferencesView(model);
        var menuItemPreferences = new MenuItem("Preferences");
        menuItemPreferences.setOnAction(actionEvent -> preferencesView.getStage().showAndWait());
        menuSimulation.getItems().add(menuItemPreferences);
        menuBar.getMenus().add(menuSimulation);

        Menu menuHelp = new Menu("_Help");
        var aboutWindow = new AboutWindow();
        var menuItemAbout = new MenuItem("About");
        menuItemAbout.setOnAction(actionEvent -> aboutWindow.getStage().showAndWait());
        menuHelp.getItems().add(menuItemAbout);
        menuBar.getMenus().add(menuHelp);

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
        menuItemNewDiagram.disableProperty().bind(Bindings.size(model.getProjects()).isEqualTo(0));
    }

    private void onOpenProjectClickedHandler(ActionEvent actionEvent) {
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select project file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Project files", "*.rgl"));
        var file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            var oldFileLoader = new OldFileLoader();
            model.getProjects().add(oldFileLoader.loadProject(file.getAbsolutePath()));
        }
    }

    private void onSaveProjectClickedHandler(ActionEvent actionEvent) {
        var directoryChooser = new DirectoryChooser();
        var project = model.selectedDiagramProperty().get().getProject();
        directoryChooser.setTitle("Select save directory for project " + project.getName());
        var file = directoryChooser.showDialog(menuBar.getScene().getWindow());
        if (file != null) {
            var oldFileSaver = new OldFileSaver();
            oldFileSaver.saveProject(file.getPath(), project);
        }
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
