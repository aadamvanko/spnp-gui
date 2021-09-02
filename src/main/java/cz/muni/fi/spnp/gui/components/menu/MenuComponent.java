package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefinesTableViewWindow;
import cz.muni.fi.spnp.gui.components.menu.views.diagrams.NewDiagramView;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionsView;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludesTableViewWindow;
import cz.muni.fi.spnp.gui.components.menu.views.preferences.PreferencesView;
import cz.muni.fi.spnp.gui.components.menu.views.projects.NewProjectView;
import cz.muni.fi.spnp.gui.components.menu.views.variables.InputParametersTableViewWindow;
import cz.muni.fi.spnp.gui.components.menu.views.variables.VariablesTableViewWindow;
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

        Menu menuProject = new Menu("Project");

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

        var menuDiagram = new Menu("Diagram");

        newDiagramView = new NewDiagramView(model);
        menuItemNewDiagram = new MenuItem("New Diagram");
        menuItemNewDiagram.setDisable(true);
        menuItemNewDiagram.setOnAction(actionEvent -> {
            newDiagramView.getStage().showAndWait();
        });
        menuDiagram.getItems().add(menuItemNewDiagram);
        menuBar.getMenus().add(menuDiagram);

        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().add(menuEdit);

        Menu menuView = new Menu("View");

        includesTableViewWindow = new IncludesTableViewWindow(model);
        menuItemViewIncludes = new MenuItem("Includes");
        menuItemViewIncludes.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewIncludes.setOnAction(actionEvent -> includesTableViewWindow.getStage().showAndWait());
        menuView.getItems().add(menuItemViewIncludes);

        definesWindowView = new DefinesTableViewWindow(model);
        menuItemViewDefines = new MenuItem("Defines");
        menuItemViewDefines.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewDefines.setOnAction(actionEvent -> definesWindowView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewDefines);

        variablesWindowView = new VariablesTableViewWindow(model);
        menuItemViewVariables = new MenuItem("Variables");
        menuItemViewVariables.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewVariables.setOnAction(actionEvent -> variablesWindowView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewVariables);

        inputParametersTableViewWindow = new InputParametersTableViewWindow(model);
        menuItemViewInputParameters = new MenuItem("Input Parameters");
        menuItemViewInputParameters.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewInputParameters.setOnAction(actionEvent -> inputParametersTableViewWindow.getStage().showAndWait());
        menuView.getItems().add(menuItemViewInputParameters);

        functionsView = new FunctionsView();
        menuItemViewFunctions = new MenuItem("Functions");
        menuItemViewFunctions.disableProperty().bind(model.selectedDiagramProperty().isNull());
        menuItemViewFunctions.setOnAction(actionEvent -> functionsView.getStage().showAndWait());
        menuView.getItems().add(menuItemViewFunctions);
        menuBar.getMenus().add(menuView);

        var menuSimulation = new Menu("Simulation");

        var preferencesView = new PreferencesView(model);
        var menuItemPreferences = new MenuItem("Preferences");
        menuItemPreferences.setOnAction(actionEvent -> preferencesView.getStage().showAndWait());
        menuSimulation.getItems().add(menuItemPreferences);
        menuBar.getMenus().add(menuSimulation);

        Menu menuHelp = new Menu("Help");
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
