package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefinesView;
import cz.muni.fi.spnp.gui.components.menu.views.diagrams.NewDiagramView;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionsView;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludesView;
import cz.muni.fi.spnp.gui.components.menu.views.preferences.PreferencesView;
import cz.muni.fi.spnp.gui.components.menu.views.projects.NewProjectView;
import cz.muni.fi.spnp.gui.components.menu.views.variables.InputParametersView;
import cz.muni.fi.spnp.gui.components.menu.views.variables.VariablesView;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MenuComponent extends ApplicationComponent {

    private final MenuBar menuBar;
    private final DefinesView definesView;
    private final MenuItem menuItemViewIncludes;
    private final IncludesView includesView;
    private final MenuItem menuItemViewDefines;
    private final VariablesView variablesView;
    private final MenuItem menuItemViewVariables;
    private final InputParametersView inputParametersView;
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

        Menu menuFile = new Menu("File");
        newProjectView = new NewProjectView(model);
        menuItemNewProject = new MenuItem("New Project");
        menuItemNewProject.setOnAction(actionEvent -> {
            newProjectView.getStage().showAndWait();
        });
        menuFile.getItems().add(menuItemNewProject);

        newDiagramView = new NewDiagramView(model);
        menuItemNewDiagram = new MenuItem("New Diagram");
        menuItemNewDiagram.setDisable(true);
        menuItemNewDiagram.setOnAction(actionEvent -> {
            newDiagramView.prepare();
            newDiagramView.getStage().showAndWait();
        });
        menuFile.getItems().add(menuItemNewDiagram);
        menuFile.getItems().add(new SeparatorMenuItem());

        var preferencesView = new PreferencesView(model);
        var menuItemPreferences = new MenuItem("Preferences");
        menuItemPreferences.setOnAction(actionEvent -> {
            preferencesView.getStage().showAndWait();
        });
        menuFile.getItems().add(menuItemPreferences);
        menuBar.getMenus().add(menuFile);

        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().add(menuEdit);

        Menu menuView = new Menu("View");

        includesView = new IncludesView();
        menuItemViewIncludes = new MenuItem("Includes");
        menuItemViewIncludes.setDisable(false);
        menuItemViewIncludes.setOnAction(actionEvent -> {
            includesView.getStage().showAndWait();
        });
        menuView.getItems().add(menuItemViewIncludes);

        definesView = new DefinesView();
        menuItemViewDefines = new MenuItem("Defines");
        menuItemViewDefines.setDisable(false);
        menuItemViewDefines.setOnAction(actionEvent -> {
            definesView.getStage().showAndWait();
        });
        menuView.getItems().add(menuItemViewDefines);

        variablesView = new VariablesView();
        menuItemViewVariables = new MenuItem("Variables");
        menuItemViewVariables.setDisable(false);
        menuItemViewVariables.setOnAction(actionEvent -> {
            variablesView.getStage().showAndWait();
        });
        menuView.getItems().add(menuItemViewVariables);

        inputParametersView = new InputParametersView();
        menuItemViewInputParameters = new MenuItem("Input Parameters");
        menuItemViewInputParameters.setDisable(false);
        menuItemViewInputParameters.setOnAction(actionEvent -> {
            inputParametersView.getStage().showAndWait();
        });
        menuView.getItems().add(menuItemViewInputParameters);

        functionsView = new FunctionsView();
        menuItemViewFunctions = new MenuItem("Functions");
        menuItemViewFunctions.setDisable(false);
        menuItemViewFunctions.setOnAction(actionEvent -> {
            functionsView.getStage().showAndWait();
        });
        menuView.getItems().add(menuItemViewFunctions);

        menuBar.getMenus().add(menuView);

        Menu menuTools = new Menu("Tools");
        menuBar.getMenus().add(menuTools);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
        menuItemNewDiagram.disableProperty().bind(Bindings.size(model.getProjects()).isEqualTo(0));
    }

    @Override
    public Node getRoot() {
        return menuBar;
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (newDiagram == null) {
            menuItemViewDefines.setDisable(true);
            return;
        } else {
            menuItemViewDefines.setDisable(false);
        }

        includesView.bindSourceCollection(newDiagram.getIncludes());
        definesView.bindSourceCollection(newDiagram.getDefines());
        variablesView.bindSourceCollection(newDiagram.getVariables());
        inputParametersView.bindSourceCollection(newDiagram.getInputParameters());
        functionsView.bindDiagramViewModel(newDiagram);
    }

}
