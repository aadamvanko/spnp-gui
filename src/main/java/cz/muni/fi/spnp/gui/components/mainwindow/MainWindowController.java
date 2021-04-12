package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.gui.components.elementsoutline.DiagramOutlineComponent;
import cz.muni.fi.spnp.gui.components.functions.FunctionsCategoriesComponent;
import cz.muni.fi.spnp.gui.components.graph.GraphComponent;
import cz.muni.fi.spnp.gui.components.menu.MenuComponent;
import cz.muni.fi.spnp.gui.components.projects.ProjectsComponent;
import cz.muni.fi.spnp.gui.components.propertieseditor.PropertiesComponent;
import cz.muni.fi.spnp.gui.components.quickactions.QuickActionsComponent;
import cz.muni.fi.spnp.gui.components.statusbar.StatusBarComponent;
import cz.muni.fi.spnp.gui.components.toolbar.ToolbarComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainWindowController {

    private final Model model;
    private final Notifications notifications;

    private final BorderPane borderPane;
    private MenuComponent menuComponent;
    private ProjectsComponent projectsComponent;
    private QuickActionsComponent quickActionsComponent;
    private DiagramOutlineComponent elementsOutlineView;
    private FunctionsCategoriesComponent functionsCategoriesComponent;
    private StatusBarComponent statusBarComponent;
    private ToolbarComponent toolbarComponent;
    private PropertiesComponent propertiesComponent;
    private GraphComponent graphComponent;

    public MainWindowController() {
        notifications = new Notifications();
        model = new Model(notifications);

        borderPane = new BorderPane();
        borderPane.setTop(createTopPanel());
        borderPane.setLeft(createLeftPanel());
        borderPane.setBottom(createBottomPanel());
        borderPane.setRight(createRightPanel());
        borderPane.setCenter(createCenterPanel());

        var project1 = new ProjectViewModel(notifications, "Project1");
        model.addProject(project1);
        var diagram1 = new DiagramViewModel(project1);
        diagram1.nameProperty().set("Diagram1");
        project1.addDiagram(diagram1);
    }

    private Node createCenterPanel() {
        toolbarComponent = new ToolbarComponent(model, notifications);
        graphComponent = new GraphComponent(model, notifications);

        VBox vBox = new VBox(toolbarComponent.getRoot(), graphComponent.getRoot());
        return vBox;
    }

    private Node createRightPanel() {
        VBox vbox = new VBox();
        propertiesComponent = new PropertiesComponent(model, notifications);
        vbox.getChildren().add(propertiesComponent.getRoot());

        functionsCategoriesComponent = new FunctionsCategoriesComponent(model, notifications);
        vbox.getChildren().add(functionsCategoriesComponent.getRoot());
        return vbox;
    }

    private Node createBottomPanel() {
        statusBarComponent = new StatusBarComponent(model, notifications);
        return statusBarComponent.getRoot();
    }

    private Node createLeftPanel() {
        VBox vbox = new VBox();

        projectsComponent = new ProjectsComponent(model, notifications);
        vbox.getChildren().add(projectsComponent.getRoot());

        elementsOutlineView = new DiagramOutlineComponent(model, notifications);
        vbox.getChildren().add(elementsOutlineView.getRoot());
        return vbox;
    }

    private Node createTopPanel() {
        VBox vbox = new VBox();
        menuComponent = new MenuComponent(model, notifications);
        vbox.getChildren().add(menuComponent.getRoot());

        quickActionsComponent = new QuickActionsComponent(model, notifications);
        vbox.getChildren().add(quickActionsComponent.getRoot());
        return vbox;
    }

    public BorderPane getRoot() {
        return borderPane;
    }

}
