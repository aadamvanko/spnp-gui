package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
import cz.muni.fi.spnp.gui.OldFileLoader;
import cz.muni.fi.spnp.gui.components.diagramoutline.DiagramOutlineComponent;
import cz.muni.fi.spnp.gui.components.functions.FunctionsCategoriesComponent;
import cz.muni.fi.spnp.gui.components.graph.GraphComponent;
import cz.muni.fi.spnp.gui.components.menu.MenuComponent;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.projects.ProjectsComponent;
import cz.muni.fi.spnp.gui.components.propertieseditor.PropertiesComponent;
import cz.muni.fi.spnp.gui.components.quickactions.QuickActionsComponent;
import cz.muni.fi.spnp.gui.components.statusbar.StatusBarComponent;
import cz.muni.fi.spnp.gui.components.toolbar.ToolbarComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.*;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

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

        var place1 = new PlaceViewModel("place1", 100, 100, 1);
        var place2 = new PlaceViewModel("place2", 500, 100, 2);
        var place3 = new PlaceViewModel("place4", 100, 200, 3);
        var timedTransition1 = new TimedTransitionViewModel("timed1", 300, 100, 1, TransitionDistributionType.Constant);
        var standardArc1 = new StandardArcViewModel("standard1", place1, timedTransition1);
        var standardArc2 = new StandardArcViewModel("standard2", timedTransition1, place2);
        var immediateTransition1 = new ImmediateTransitionViewModel("immediate1", 300, 200, 1);
        var inhibitorArc1 = new InhibitorArcViewModel("inhibitor1", place3, immediateTransition1);
        var place4 = new PlaceViewModel("place4", 100, 500, 4);
        var elements = Arrays.asList(place1, place2, place3, timedTransition1, standardArc1, standardArc2, immediateTransition1, inhibitorArc1, place4);

        var defines = new ArrayList<DefineViewModel>();
        defines.add(new DefineViewModel("MAX_SIZE", "10"));
        defines.add(new DefineViewModel("MIN_SIZE", "-4"));

        var functions = new ArrayList<FunctionViewModel>();
        functions.add(new FunctionViewModel("function1", FunctionType.Guard, "int x = 3;"));
        functions.add(new FunctionViewModel("function2", FunctionType.Generic, "double d = 10;"));

        var diagram1 = new DiagramViewModel(notifications, project1, elements, defines, functions);
        diagram1.nameProperty().set("diagram1");
        project1.addDiagram(diagram1);

        var diagram2 = new DiagramViewModel(notifications, project1);
        diagram2.nameProperty().set("diagram2");
        project1.addDiagram(diagram2);

        OldFileLoader loader = new OldFileLoader(notifications);
        var project1x = loader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\project1.rgl");
        model.addProject(project1x);

        model.selectDiagram(diagram1);

        //
//        PlaceController pc1 = new PlaceController(100, 100);
//        PlaceController pc2 = new PlaceController(500, 100);
//        PlaceController pc4 = new PlaceController(100, 200);
//        TimedTransitionController ttc1 = new TimedTransitionController(300, 100);
//        ArcController ac1 = new StandardArcController(pc1, ttc1);
//        ArcController ac2 = new StandardArcController(ttc1, pc2);
//        ImmediateTransitionController itc1 = new ImmediateTransitionController(300, 200);
//        ArcController ac3 = new InhibitorArcController(pc4, itc1);
//        PlaceController pc3 = new PlaceController(100, 500);
//        var diagram1 = new DiagramViewModel(project1);
//        diagram1.nameProperty().set("Diagram1");
//        project1.addDiagram(diagram1);
    }

    private Node createCenterPanel() {
        toolbarComponent = new ToolbarComponent(model, notifications);
        graphComponent = new GraphComponent(model, notifications);

        VBox vBox = new VBox(toolbarComponent.getRoot(), graphComponent.getRoot());

        var graphComponentRegion = (Region) graphComponent.getRoot();
        graphComponentRegion.prefWidthProperty().bind(vBox.widthProperty());
        graphComponentRegion.prefHeightProperty().bind(vBox.heightProperty());

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
