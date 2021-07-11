package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.mappers.DiagramMapper;
import cz.muni.fi.spnp.gui.storing.loaders.OldFileLoader;
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
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ConstantTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ConstantTransitionDistributionViewModel;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

        var project1 = new ProjectViewModel(notifications);
        project1.nameProperty().set("mock_project1");
        model.getProjects().add(project1);

        var place1 = new PlaceViewModel();
        place1.nameProperty().set("place1");
        place1.positionXProperty().set(100);
        place1.positionYProperty().set(100);
        place1.numberOfTokensProperty().set("1");

        var place2 = new PlaceViewModel();
        place2.nameProperty().set("place2");
        place2.positionXProperty().set(500);
        place2.positionYProperty().set(100);
        place2.numberOfTokensProperty().set("2");

        var place3 = new PlaceViewModel();
        place3.nameProperty().set("place4");
        place3.positionXProperty().set(100);
        place3.positionYProperty().set(200);
        place3.numberOfTokensProperty().set("3");

        var timedTransition1 = new TimedTransitionViewModel();
        timedTransition1.nameProperty().set("timed1");
        timedTransition1.positionXProperty().set(300);
        timedTransition1.positionYProperty().set(100);
        timedTransition1.priorityProperty().set(1);
        timedTransition1.timedDistributionTypeProperty().set(TimedDistributionType.Constant);
        timedTransition1.setTransitionDistribution(new ConstantTransitionDistributionViewModel("0.4"));

        var standardArc1 = new StandardArcViewModel("standard1", place1, timedTransition1, Collections.emptyList());
        var standardArc2 = new StandardArcViewModel("standard2", timedTransition1, place2, Collections.emptyList());

        var immediateTransition1 = new ImmediateTransitionViewModel();
        immediateTransition1.nameProperty().set("immediate1");
        immediateTransition1.positionXProperty().set(300);
        immediateTransition1.positionYProperty().set(200);
        immediateTransition1.priorityProperty().set(1);
        immediateTransition1.setTransitionProbability(new ConstantTransitionProbabilityViewModel());

        var inhibitorArc1 = new InhibitorArcViewModel("inhibitor1", place3, immediateTransition1, Collections.emptyList());

        var place4 = new PlaceViewModel();
        place4.nameProperty().set("place4");
        place4.positionXProperty().set(100);
        place4.positionYProperty().set(500);
        place4.numberOfTokensProperty().set("4");

        var elements = Arrays.asList(place1, place2, place3, timedTransition1, standardArc1, standardArc2, immediateTransition1, inhibitorArc1, place4);

        var includes = new ArrayList<IncludeViewModel>();
        includes.add(new IncludeViewModel("<stdio.h>"));
        includes.add(new IncludeViewModel("\"user.h\""));

        var defines = new ArrayList<DefineViewModel>();
        defines.add(new DefineViewModel("MAX_SIZE", "10"));
        defines.add(new DefineViewModel("MIN_SIZE", "-4"));

        var functions = new ArrayList<FunctionViewModel>();
        functions.add(new FunctionViewModel("function1", FunctionType.Guard, "int x = 3;", FunctionReturnType.VOID, false));
        functions.add(new FunctionViewModel("function2", FunctionType.Generic, "double d = 10;", FunctionReturnType.VOID, false));

        var variables = new ArrayList<VariableViewModel>();
        variables.add(new VariableViewModel("var_1_global_int", VariableType.Global, VariableDataType.INT, "10"));
        variables.add(new VariableViewModel("var_1_global_double", VariableType.Global, VariableDataType.DOUBLE, "10.25"));
        variables.add(new VariableViewModel("var_1_param_int", VariableType.Parameter, VariableDataType.INT, "11"));
        variables.add(new VariableViewModel("var_1_param_double", VariableType.Parameter, VariableDataType.DOUBLE, "11.25"));

        var inputParameters = new ArrayList<InputParameterViewModel>();
        inputParameters.add(new InputParameterViewModel("input_param_1_int", VariableDataType.INT, "Enter int value for input param"));
        inputParameters.add(new InputParameterViewModel("input_param_1_double", VariableDataType.DOUBLE, "Enter double value for input param"));

        var diagram1 = new DiagramViewModel(notifications, project1, elements, includes, defines, variables, inputParameters, functions);
        diagram1.nameProperty().set("mock_diagram1");
        project1.getDiagrams().add(diagram1);

        var diagram2 = new DiagramViewModel(notifications, project1);
        diagram2.nameProperty().set("mock_diagram2");
        project1.getDiagrams().add(diagram2);

        var diagramMapper = new DiagramMapper(diagram1);
        var petriNet = diagramMapper.createPetriNet();
        var spnpCode = diagramMapper.createSPNPCode();
        var spnpOptions = diagramMapper.createSPNPOptions();

        var oldFileLoader = new OldFileLoader(notifications);
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P0.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P2.rgl");
        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P3.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P5.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P8.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P20.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\project1.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\functionsExampleProject.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\definesTestProject.rgl");
//        var oldFileSaver = new OldFileSaver();
//        oldFileSaver.saveProject("C:\\Spnp-Gui\\Examples-Official\\test\\project1_saved.rgl", project1x);
        model.getProjects().add(project1x);

        model.selectedDiagramProperty().set(diagram1);

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
