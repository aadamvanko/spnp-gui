package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.diagramoutline.DiagramOutlineComponent;
import cz.muni.fi.spnp.gui.components.functions.FunctionsCategoriesComponent;
import cz.muni.fi.spnp.gui.components.graph.DiagramComponent;
import cz.muni.fi.spnp.gui.components.menu.MenuComponent;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.components.projects.ProjectsComponent;
import cz.muni.fi.spnp.gui.components.propertieseditor.PropertiesComponent;
import cz.muni.fi.spnp.gui.components.quickactions.QuickActionsComponent;
import cz.muni.fi.spnp.gui.components.statusbar.StatusBarComponent;
import cz.muni.fi.spnp.gui.components.toolbar.ToolbarComponent;
import cz.muni.fi.spnp.gui.mappers.DiagramMapper;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.storing.loaders.OldFileLoader;
import cz.muni.fi.spnp.gui.storing.savers.OldFileSaver;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ConstantTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ConstantTransitionDistributionViewModel;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainWindowView {

    private final Model model;

    private Pane pane;
    private MenuComponent menuComponent;
    private ProjectsComponent projectsComponent;
    private QuickActionsComponent quickActionsComponent;
    private DiagramOutlineComponent elementsOutlineView;
    private FunctionsCategoriesComponent functionsCategoriesComponent;
    private StatusBarComponent statusBarComponent;
    private ToolbarComponent toolbarComponent;
    private PropertiesComponent propertiesComponent;
    private DiagramComponent diagramComponent;

    public MainWindowView(Model model) {
        this.model = model;

        createView();

        var project1 = new ProjectViewModel();
        project1.nameProperty().set("mock_project1");
        model.getProjects().add(project1);

        var functions = new ArrayList<FunctionViewModel>();
        functions.add(new FunctionViewModel("function1", FunctionType.Guard, "int x = 3;", FunctionReturnType.VOID, false, true));
        functions.add(new FunctionViewModel("function2", FunctionType.Generic, "double d = 10;", FunctionReturnType.VOID, false, true));
        functions.add(new FunctionViewModel("function_prob_1", FunctionType.Probability, "return 0.4;", FunctionReturnType.DOUBLE, false, true));
        functions.add(new FunctionViewModel("function_arc_card", FunctionType.ArcCardinality, "return 5;", FunctionReturnType.INT, false, true));
        functions.add(new FunctionViewModel("function_dist", FunctionType.Distribution, "return 0.7;", FunctionReturnType.DOUBLE, false, true));

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
        var constantDistribution = new ConstantTransitionDistributionViewModel("0.4");
        constantDistribution.valueProperty().set("0.59");
        timedTransition1.setTransitionDistribution(constantDistribution);

        var standardArc1 = new StandardArcViewModel("standard1", place1, timedTransition1,
                List.of(new DragPointViewModel(200, 50), new DragPointViewModel(250, 75)));
        var standardArc2 = new StandardArcViewModel("standard2", timedTransition1, place2, Collections.emptyList());

        var immediateTransition1 = new ImmediateTransitionViewModel();
        immediateTransition1.nameProperty().set("immediate1");
        immediateTransition1.positionXProperty().set(300);
        immediateTransition1.positionYProperty().set(200);
        immediateTransition1.priorityProperty().set(1);
        var constantProbability = new ConstantTransitionProbabilityViewModel();
        constantProbability.valueProperty().set(12.77);
        immediateTransition1.setTransitionProbability(constantProbability);
        immediateTransition1.guardFunctionProperty().set(functions.get(0));

        var inhibitorArc1 = new InhibitorArcViewModel("inhibitor1", place3, immediateTransition1, Collections.emptyList());

        var immediateTransition2 = new ImmediateTransitionViewModel();
        immediateTransition2.nameProperty().set("immediate2");
        immediateTransition2.positionXProperty().set(300);
        immediateTransition2.positionYProperty().set(300);
        immediateTransition2.priorityProperty().set(1);
        var constantProbability2 = new ConstantTransitionProbabilityViewModel();
        constantProbability2.valueProperty().set(1.78);
        immediateTransition2.setTransitionProbability(constantProbability2);
        immediateTransition2.guardFunctionProperty().set(functions.get(0));

        var place4 = new PlaceViewModel();
        place4.nameProperty().set("place4");
        place4.positionXProperty().set(100);
        place4.positionYProperty().set(500);
        place4.numberOfTokensProperty().set("4");

        var elements = Arrays.asList(place1, place2, place3, timedTransition1, standardArc1, standardArc2, immediateTransition1, immediateTransition2, inhibitorArc1, place4);

        var includes = new ArrayList<IncludeViewModel>();
        includes.add(new IncludeViewModel("<stdio.h>"));
        includes.add(new IncludeViewModel("\"user.h\""));

        var defines = new ArrayList<DefineViewModel>();
        defines.add(new DefineViewModel("MAX_SIZE", "10"));
        defines.add(new DefineViewModel("MIN_SIZE", "-4"));


        var variables = new ArrayList<VariableViewModel>();
        variables.add(new VariableViewModel("var_1_global_int", VariableType.Global, VariableDataType.INT, "10"));
        variables.add(new VariableViewModel("var_1_global_double", VariableType.Global, VariableDataType.DOUBLE, "10.25"));
        variables.add(new VariableViewModel("var_1_param_int", VariableType.Parameter, VariableDataType.INT, "11"));
        variables.add(new VariableViewModel("var_1_param_double", VariableType.Parameter, VariableDataType.DOUBLE, "11.25"));

        var inputParameters = new ArrayList<InputParameterViewModel>();
        inputParameters.add(new InputParameterViewModel("input_param_1_int", VariableDataType.INT, "Enter int value for input param"));
        inputParameters.add(new InputParameterViewModel("input_param_1_double", VariableDataType.DOUBLE, "Enter double value for input param"));

        var diagram1 = new DiagramViewModel(project1, elements, includes, defines, variables, inputParameters, functions);
        diagram1.nameProperty().set("mock_diagram1");
        project1.getDiagrams().add(diagram1);

        var diagram2 = new DiagramViewModel(project1);
        diagram2.nameProperty().set("mock_diagram2");
        project1.getDiagrams().add(diagram2);

        var d2_place1 = new PlaceViewModel();
        d2_place1.positionXProperty().set(30);
        d2_place1.positionYProperty().set(30);
        diagram2.getElements().add(d2_place1);

        var diagramMapper = new DiagramMapper(diagram1);
        var petriNet = diagramMapper.createPetriNet();
        var spnpCode = diagramMapper.createSPNPCode();
        var spnpOptions = diagramMapper.createSPNPOptions();

        var oldFileLoader = new OldFileLoader();
        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P0.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P2.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P3.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P5.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P8.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\P20.rgl"); // expressions in timed transition values
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\project1.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\functionsExampleProject.rgl");
//        var project1x = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\definesTestProject.rgl");
//        var oldFileSaver = new OldFileSaver();
//        oldFileSaver.saveProject("C:\\Spnp-Gui\\Examples-Official\\test\\project1_saved.rgl", project1x);
        model.getProjects().add(project1x);

        var definesTestProject = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\all_things.rgl");
        var oldFileSaver = new OldFileSaver();
        oldFileSaver.saveProject("C:\\Spnp-Gui\\Examples-Official\\saved", definesTestProject);

        var project1AllTransitions = oldFileLoader.loadProject("C:\\Spnp-Gui\\Examples-Official\\test\\project1.rgl");
        model.getProjects().add(project1AllTransitions);

        model.selectedDiagramProperty().set(diagram1);
    }

    private void createView() {
        var middleSplitPane = new SplitPane();
        middleSplitPane.setOrientation(Orientation.HORIZONTAL);
        middleSplitPane.getItems().add(createLeftPanel());
        middleSplitPane.getItems().add(createCenterPanel());
        middleSplitPane.getItems().add(createRightPanel());
        middleSplitPane.setDividerPositions(0.2, 0.75);

        var mainSplitPane = new SplitPane();
        mainSplitPane.setOrientation(Orientation.VERTICAL);
        mainSplitPane.getItems().add(middleSplitPane);
        mainSplitPane.getItems().add(createBottomPanel());
        mainSplitPane.setDividerPositions(0.9);
        VBox.setVgrow(mainSplitPane, Priority.ALWAYS);

        pane = new AnchorPane();

        var vBoxAll = new VBox();
        vBoxAll.getChildren().add(createTopPanel());
        vBoxAll.getChildren().add(mainSplitPane);
        pane.getChildren().add(vBoxAll);

        AnchorPane.setLeftAnchor(vBoxAll, 0.0);
        AnchorPane.setTopAnchor(vBoxAll, 0.0);
        AnchorPane.setBottomAnchor(vBoxAll, 0.0);
        AnchorPane.setRightAnchor(vBoxAll, 0.0);
    }

    private Node createCenterPanel() {
        toolbarComponent = new ToolbarComponent(model);
        diagramComponent = new DiagramComponent(model);

        VBox vBox = new VBox(toolbarComponent.getRoot(), diagramComponent.getRoot());

        var graphComponentRegion = (Region) diagramComponent.getRoot();
        graphComponentRegion.prefWidthProperty().bind(vBox.widthProperty());
        graphComponentRegion.prefHeightProperty().bind(vBox.heightProperty());

        return vBox;
    }

    private Node createRightPanel() {
        var vbox = new VBox();
        propertiesComponent = new PropertiesComponent(model);
        vbox.getChildren().add(propertiesComponent.getRoot());

        functionsCategoriesComponent = new FunctionsCategoriesComponent(model);
        vbox.getChildren().add(functionsCategoriesComponent.getRoot());

        vbox.setPrefWidth(350);
        return vbox;
    }

    private Node createBottomPanel() {
        statusBarComponent = new StatusBarComponent(model);
        return statusBarComponent.getRoot();
    }

    private Node createLeftPanel() {
        var vbox = new VBox();

        projectsComponent = new ProjectsComponent(model);
        vbox.getChildren().add(projectsComponent.getRoot());

        elementsOutlineView = new DiagramOutlineComponent(model);
        vbox.getChildren().add(elementsOutlineView.getRoot());
        return vbox;
    }

    private Node createTopPanel() {
        VBox vbox = new VBox();
        menuComponent = new MenuComponent(model);
        vbox.getChildren().add(menuComponent.getRoot());

        quickActionsComponent = new QuickActionsComponent(model);
        vbox.getChildren().add(quickActionsComponent.getRoot());
        return vbox;
    }

    public Pane getRoot() {
        return pane;
    }

}