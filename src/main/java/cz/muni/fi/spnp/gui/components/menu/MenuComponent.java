package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefinesView2;
import cz.muni.fi.spnp.gui.components.menu.views.diagrams.NewDiagramView;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionsView;
import cz.muni.fi.spnp.gui.components.menu.views.projects.NewProjectView;
import cz.muni.fi.spnp.gui.components.menu.views.variables.VariablesView;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.NewProjectAddedListener;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.notifications.SelectedDiagramChangeListener;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuComponent extends ApplicationComponent implements SelectedDiagramChangeListener, NewProjectAddedListener {

    private final MenuBar menuBar;
    private final DefinesView2 definesView;
    private final MenuItem menuItemViewDefines;
    private final VariablesView variablesView;
    private final MenuItem menuItemViewVariables;
    private final FunctionsView functionsView;
    private final MenuItem menuItemViewFunctions;
    private final NewProjectView newProjectView;
    private final MenuItem menuItemNewProject;
    private final NewDiagramView newDiagramView;
    private final MenuItem menuItemNewDiagram;

    public MenuComponent(Model model, Notifications notifications) {
        super(model, notifications);

        menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        newProjectView = new NewProjectView(model, notifications);
        menuItemNewProject = new MenuItem("New Project");
        menuItemNewProject.setOnAction(actionEvent -> {
            newProjectView.getStage().showAndWait();
        });
        menuFile.getItems().add(menuItemNewProject);

        newDiagramView = new NewDiagramView(model, notifications);
        menuItemNewDiagram = new MenuItem("New Diagram");
        menuItemNewDiagram.setDisable(true);
        menuItemNewDiagram.setOnAction(actionEvent -> {
            newDiagramView.prepare();
            newDiagramView.getStage().showAndWait();
        });
        menuFile.getItems().add(menuItemNewDiagram);
        menuBar.getMenus().add(menuFile);

        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().add(menuEdit);

        Menu menuView = new Menu("View");

        definesView = new DefinesView2();
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

        notifications.addSelectedDiagramChangeListener(this);
        notifications.addNewProjectAddedListener(this);
    }

    @Override
    public Node getRoot() {
        return menuBar;
    }

    @Override
    public void onSelectedDiagramChanged(DiagramViewModel diagramViewModel) {
        if (diagramViewModel != null) {
            menuItemViewDefines.setDisable(false);
        }

        definesView.bindDiagramViewModel(diagramViewModel);
        variablesView.bindDiagramViewModel(diagramViewModel);
    }

    @Override
    public void onNewProjectAdded(ProjectViewModel projectViewModel) {
        menuItemNewDiagram.setDisable(false);
    }
}
