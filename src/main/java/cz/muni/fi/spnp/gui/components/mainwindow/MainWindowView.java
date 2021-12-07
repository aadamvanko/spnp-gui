package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.gui.components.diagram.DiagramComponent;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewMode;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.toolbar.ToolbarComponent;
import cz.muni.fi.spnp.gui.components.diagramoutline.DiagramOutlineComponent;
import cz.muni.fi.spnp.gui.components.functions.FunctionsCategoriesComponent;
import cz.muni.fi.spnp.gui.components.menu.MenuComponent;
import cz.muni.fi.spnp.gui.components.menu.view.defines.DefinesCollapsableView;
import cz.muni.fi.spnp.gui.components.menu.view.includes.IncludesCollapsableView;
import cz.muni.fi.spnp.gui.components.menu.view.inputparameters.InputParametersCollapsableView;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariablesCollapsableView;
import cz.muni.fi.spnp.gui.components.projects.ProjectsComponent;
import cz.muni.fi.spnp.gui.components.propertieseditor.PropertiesComponent;
import cz.muni.fi.spnp.gui.components.statusbar.StatusBarComponent;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;

import java.io.BufferedOutputStream;
import java.io.PrintStream;

/**
 * Main window containing all the components.
 */
public class MainWindowView {

    private final Model model;

    private Pane pane;
    private final ChangeListener<DiagramViewMode> onViewModeChangedListener;
    private VBox leftVBox;

    // components
    private MenuComponent menuComponent;
    private ProjectsComponent projectsComponent;
    private DiagramOutlineComponent diagramOutlineComponent;
    private FunctionsCategoriesComponent functionsCategoriesComponent;
    private StatusBarComponent statusBarComponent;
    private ToolbarComponent toolbarComponent;
    private PropertiesComponent propertiesComponent;
    private DiagramComponent diagramComponent;

    // side panel views for the items
    private IncludesCollapsableView includesCollapsableView;
    private DefinesCollapsableView definesCollapsableView;
    private VariablesCollapsableView variablesCollapsableView;
    private InputParametersCollapsableView inputParametersCollapsableView;
    private VBox rightVBox;

    public MainWindowView(Model model) {
        this.model = model;

        createView();

        // redirect stdout and stderr to the output panel
        var textAreaOutputStream = new BufferedOutputStream(new TextAreaOutputStream(statusBarComponent.getTextArea()));
        var printStream = new PrintStream(textAreaOutputStream, true);
        System.setOut(printStream);
        System.setErr(printStream);

        this.onViewModeChangedListener = this::onViewModeChangedListener;

        // add handler to the selected diagram property
        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChangedListener);
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

    private void onSelectedDiagramChangedListener(Observable observable, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (oldDiagram != null) {
            oldDiagram.viewModeProperty().removeListener(this.onViewModeChangedListener);
        }

        if (newDiagram == null) {
            // unbind previous diagram
            includesCollapsableView.bindSourceCollection(FXCollections.emptyObservableList());
            definesCollapsableView.bindSourceCollection(FXCollections.emptyObservableList());
            variablesCollapsableView.bindSourceCollection(FXCollections.emptyObservableList());
            inputParametersCollapsableView.bindSourceCollection(FXCollections.emptyObservableList());

            // disable views
            includesCollapsableView.getRoot().setDisable(true);
            definesCollapsableView.getRoot().setDisable(true);
            variablesCollapsableView.getRoot().setDisable(true);
            inputParametersCollapsableView.getRoot().setDisable(true);
            functionsCategoriesComponent.getRoot().setDisable(true);

            return;
        }

        // enable views
        includesCollapsableView.getRoot().setDisable(false);
        definesCollapsableView.getRoot().setDisable(false);
        variablesCollapsableView.getRoot().setDisable(false);
        inputParametersCollapsableView.getRoot().setDisable(false);
        functionsCategoriesComponent.getRoot().setDisable(false);

        // bind new diagram
        includesCollapsableView.bindSourceCollection(newDiagram.getIncludes());
        definesCollapsableView.bindSourceCollection(newDiagram.getDefines());
        variablesCollapsableView.bindSourceCollection(newDiagram.getVariables());
        inputParametersCollapsableView.bindSourceCollection(newDiagram.getInputParameters());

        newDiagram.viewModeProperty().addListener(this.onViewModeChangedListener);
        onViewModeChangedListener(null, null, newDiagram.getViewMode());
    }

    private void onViewModeChangedListener(ObservableValue<? extends DiagramViewMode> observableValue, DiagramViewMode oldValue, DiagramViewMode newValue) {
        leftVBox.getChildren().remove(diagramOutlineComponent.getRoot());
        leftVBox.getChildren().remove(functionsCategoriesComponent.getRoot());

        rightVBox.getChildren().clear();

        // change the content of the side panels depending on the diagram view mode
        if (newValue == DiagramViewMode.CODE) {
            leftVBox.getChildren().add(functionsCategoriesComponent.getRoot());

            rightVBox.getChildren().add(includesCollapsableView.getRoot());
            rightVBox.getChildren().add(definesCollapsableView.getRoot());
            rightVBox.getChildren().add(variablesCollapsableView.getRoot());
//            rightVBox.getChildren().add(inputParametersCollapsableView.getRoot());
        } else {
            leftVBox.getChildren().add(diagramOutlineComponent.getRoot());

            rightVBox.getChildren().add(propertiesComponent.getRoot());
            rightVBox.getChildren().add(functionsCategoriesComponent.getRoot());
        }
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
        rightVBox = new VBox();

        propertiesComponent = new PropertiesComponent(model);
        rightVBox.getChildren().add(propertiesComponent.getRoot());

        functionsCategoriesComponent = new FunctionsCategoriesComponent(model);
        rightVBox.getChildren().add(functionsCategoriesComponent.getRoot());

        includesCollapsableView = new IncludesCollapsableView(model);
        rightVBox.getChildren().add(includesCollapsableView.getRoot());

        definesCollapsableView = new DefinesCollapsableView(model);
        rightVBox.getChildren().add(definesCollapsableView.getRoot());

        variablesCollapsableView = new VariablesCollapsableView(model);
        rightVBox.getChildren().add(variablesCollapsableView.getRoot());

        inputParametersCollapsableView = new InputParametersCollapsableView(model);
//        rightVBox.getChildren().add(inputParametersCollapsableView.getRoot());

        rightVBox.setPrefWidth(350);
        return rightVBox;
    }

    private Node createBottomPanel() {
        statusBarComponent = new StatusBarComponent(model);
        return statusBarComponent.getRoot();
    }

    private Node createLeftPanel() {
        leftVBox = new VBox();

        projectsComponent = new ProjectsComponent(model);
        leftVBox.getChildren().add(projectsComponent.getRoot());

        diagramOutlineComponent = new DiagramOutlineComponent(model);
        leftVBox.getChildren().add(diagramOutlineComponent.getRoot());

        return leftVBox;
    }

    private Node createTopPanel() {
        VBox vbox = new VBox();

        menuComponent = new MenuComponent(model, diagramComponent);
        vbox.getChildren().add(menuComponent.getRoot());

        return vbox;
    }

    public Pane getRoot() {
        return pane;
    }

}
