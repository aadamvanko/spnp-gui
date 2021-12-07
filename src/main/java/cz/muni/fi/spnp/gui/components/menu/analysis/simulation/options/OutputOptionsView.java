package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.common.DialogMessages;
import cz.muni.fi.spnp.gui.components.common.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.TransitionViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.VariableViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.*;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.PlaceViewModelStringConverter;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.stream.Collectors;

import static java.lang.Double.MAX_VALUE;

/**
 * Window for the output options setup.
 */
public class OutputOptionsView extends UIWindowComponent {

    private final Model model;
    private final DiagramViewModel diagramViewModel;
    private ListView<OutputOptionViewModel> listViewOptions;
    private ListView<OutputOptionViewModel> listViewSelected;
    private OutputOptionViewModel optionViewModel;
    private GridPane gridPaneProperties;
    private Button buttonAdd;
    private Button buttonDelete;

    private Label labelOptionTitle;
    private Label labelTime;
    private TextField textFieldTime;
    private Label labelPlace;
    private ChoiceBox<PlaceViewModel> choiceBoxPlace;
    private Label labelTransition;
    private ChoiceBox<TransitionViewModel> choiceBoxTransition;
    private Label labelVariable;
    private ChoiceBox<VariableViewModel> choiceBoxVariable;
    private Label labelFunction;
    private ChoiceBox<FunctionViewModel> choiceBoxFunction;

    public OutputOptionsView(Model model, DiagramViewModel diagramViewModel, boolean load) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;

        createView();

        if (load) {
            listViewSelected.getItems().addAll(model.getOutputOptions());
        }
    }

    private void createView() {
        listViewOptions = new ListView<>();
        VBox.setVgrow(listViewOptions, Priority.ALWAYS);
        HBox.setHgrow(listViewOptions, Priority.ALWAYS);
        listViewOptions.getItems().add(new ExpectedNumberOfTokensPlaceSteadyState());
        listViewOptions.getItems().add(new ExpectedNumberOfTokensPlaceTime());
        listViewOptions.getItems().add(new ExpectedNumberOfTokensAllPlacesSteadyState());
        listViewOptions.getItems().add(new ExpectedNumberOfTokensAllPlacesTime());
        listViewOptions.getItems().add(new ThroughputTransitionSteadyState());
        listViewOptions.getItems().add(new ThroughputTransitionTime());
        listViewOptions.getItems().add(new ThroughputAllTransitionsSteadyState());
        listViewOptions.getItems().add(new ThroughputAllTransitionsTime());
        listViewOptions.getItems().add(new UtilizationTransitionSteadyState());
        listViewOptions.getItems().add(new UtilizationTransitionTime());
        listViewOptions.getItems().add(new UtilizationAllTransitionsSteadyState());
        listViewOptions.getItems().add(new UtilizationAllTransitionsTime());
        listViewOptions.getItems().add(new ProbabilityPlaceIsEmptySteadyState());
        listViewOptions.getItems().add(new ProbabilityPlaceIsEmptyTime());
        listViewOptions.getItems().add(new ProbabilityAllPlacesAreEmptySteadyState());
        listViewOptions.getItems().add(new ProbabilityAllPlacesAreEmptyTime());
        listViewOptions.getItems().add(new ExpectedRewardRateSteadyState());
        listViewOptions.getItems().add(new ExpectedRewardRateTime());
        listViewOptions.getItems().add(new ExpectedAccumulatedRateUntilAbsorption());
        listViewOptions.getItems().add(new ExpectedAccumulatedRewardTime());
        listViewOptions.getItems().add(new SteadyStateAnalysisDefaultMeasures());
        listViewOptions.getItems().add(new ValueOfVariable());
        listViewOptions.getSelectionModel().selectedItemProperty().addListener(this::onOptionsSelectedItemChangedListener);

        var optionsPane = new TitledPane("Outputs:", listViewOptions);
        optionsPane.setCollapsible(false);
        VBox.setVgrow(optionsPane, Priority.ALWAYS);
        HBox.setHgrow(optionsPane, Priority.ALWAYS);
        optionsPane.setMaxHeight(MAX_VALUE);

        listViewSelected = new ListView<>();
        VBox.setVgrow(listViewSelected, Priority.ALWAYS);
        HBox.setHgrow(listViewSelected, Priority.ALWAYS);
        listViewSelected.getSelectionModel().selectedItemProperty().addListener(this::onSelectedSelectedItemChangedListener);

        var selectedPane = new TitledPane("Selected:", listViewSelected);
        selectedPane.setCollapsible(false);
        VBox.setVgrow(selectedPane, Priority.ALWAYS);
        HBox.setHgrow(selectedPane, Priority.ALWAYS);
        selectedPane.setMaxHeight(MAX_VALUE);

        var splitPane = new SplitPane(optionsPane, selectedPane);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        HBox.setHgrow(splitPane, Priority.ALWAYS);

        gridPaneProperties = new GridPane();
        VBox.setVgrow(gridPaneProperties, Priority.ALWAYS);
        HBox.setHgrow(gridPaneProperties, Priority.ALWAYS);
        gridPaneProperties.setPadding(new Insets(5));
        gridPaneProperties.setVgap(5);
        gridPaneProperties.setHgap(5);

        labelOptionTitle = new Label("");
        labelOptionTitle.setPadding(new Insets(5));

        var propertiesVBox = new VBox(labelOptionTitle, gridPaneProperties);

        var propertiesPane = new TitledPane("Properties", propertiesVBox);
        propertiesPane.setCollapsible(false);
        propertiesPane.setPrefHeight(150);

        buttonAdd = new Button("Add to selected");
        buttonAdd.setOnAction(this::onButtonAddHandler);
        buttonDelete = new Button("Delete from selected");
        buttonDelete.setOnAction(this::onButtonDeleteHandler);
        var buttonPreviewAndRun = new Button("Preview and run");
        buttonPreviewAndRun.setOnAction(this::onButtonPreviewAndRunHandler);
        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        var buttonClose = new Button("Close");
        buttonClose.setOnAction(this::onButtonCloseHandler);

        var buttonsPane = new HBox(buttonAdd, buttonDelete, buttonPreviewAndRun, spacer, buttonClose);
        buttonsPane.setSpacing(5);

        var vbox = new VBox(splitPane, propertiesPane, buttonsPane);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5));

        var scene = new Scene(vbox);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        stage.setTitle("Output Options");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(750);

        labelTime = new Label("Time:");
        textFieldTime = new TextField("");
        textFieldTime.setPrefWidth(500);
        textFieldTime.setPromptText("To use the loop feature, enter time as start time, stop value, increment value");

        labelPlace = new Label("Place:");
        choiceBoxPlace = new ChoiceBox<>();
        choiceBoxPlace.setConverter(new PlaceViewModelStringConverter(diagramViewModel));
        choiceBoxPlace.setItems(FXCollections.observableArrayList(diagramViewModel.getPlaces()));

        labelTransition = new Label("Transition:");
        choiceBoxTransition = new ChoiceBox<>();
        choiceBoxTransition.setConverter(new TransitionViewModelStringConverter(diagramViewModel));
        choiceBoxTransition.setItems(FXCollections.observableArrayList(diagramViewModel.getTransitions()));

        labelVariable = new Label("Variable:");
        choiceBoxVariable = new ChoiceBox<>();
        choiceBoxVariable.setConverter(new VariableViewModelStringConverter(diagramViewModel));
        choiceBoxVariable.setItems(diagramViewModel.getVariables());

        labelFunction = new Label("Reward function:");
        choiceBoxFunction = new ChoiceBox<>();
        choiceBoxFunction.setConverter(new FunctionViewModelStringConverter(diagramViewModel));
        var rewardFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.Reward)
                .collect(Collectors.toList());
        choiceBoxFunction.setItems(FXCollections.observableArrayList(rewardFunctions));
    }

    private void onButtonAddHandler(ActionEvent actionEvent) {
        var selectedItem = listViewOptions.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        if (optionViewModel.timeProperty() != null && textFieldTime.textProperty().get().isBlank()) {
            showError("Time cannot be blank!");
            return;
        }

        if (optionViewModel.placeProperty() != null && choiceBoxPlace.getValue() == null) {
            showErrorChoiceBox("Place");
            return;
        }

        if (optionViewModel.transitionProperty() != null && choiceBoxTransition.getValue() == null) {
            showErrorChoiceBox("Transition");
            return;
        }

        if (optionViewModel.variableProperty() != null && choiceBoxVariable.getValue() == null) {
            showErrorChoiceBox("Variable");
            return;
        }

        if (optionViewModel.functionProperty() != null && choiceBoxFunction.getValue() == null) {
            showErrorChoiceBox("Function");
            return;
        }

        var copy = optionViewModel.cleanCopy();
        optionViewModel.copyTo(copy);
        listViewSelected.getItems().add(copy);
        optionViewModel.reset();
    }

    private void showErrorChoiceBox(String selectedTypeName) {
        showError(selectedTypeName + " is not selected!");
    }

    private void showError(String errorMessage) {
        DialogMessages.showError(errorMessage, "Invalid output option's properties");
    }

    private void onButtonDeleteHandler(ActionEvent actionEvent) {
        var selectedItem = listViewSelected.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        listViewSelected.getItems().remove(selectedItem);
        if (listViewSelected.getItems().isEmpty()) {
            changeOptionViewModel(null);
        }
    }

    private void onButtonPreviewAndRunHandler(ActionEvent actionEvent) {
        unbindOptionViewModel();
        model.getOutputOptions().clear();
        model.getOutputOptions().addAll(listViewSelected.getItems());
        stage.close();

        var codePreviewView = new CodePreviewView(model, diagramViewModel);
        codePreviewView.getStage().show();
    }

    private void onButtonCloseHandler(ActionEvent actionEvent) {
        stage.close();
    }

    private void onOptionsSelectedItemChangedListener(ObservableValue<? extends OutputOptionViewModel> observableValue,
                                                      OutputOptionViewModel oldValue, OutputOptionViewModel newValue) {
        changeOptionViewModel(newValue);
        buttonAdd.setDisable(false);
        buttonDelete.setDisable(true);
    }

    private void onSelectedSelectedItemChangedListener(ObservableValue<? extends OutputOptionViewModel> observableValue,
                                                       OutputOptionViewModel oldValue, OutputOptionViewModel newValue) {
        changeOptionViewModel(newValue);
        buttonAdd.setDisable(true);
        buttonDelete.setDisable(false);
    }

    private void changeOptionViewModel(OutputOptionViewModel newOptionViewModel) {
        unbindOptionViewModel();
        gridPaneProperties.getChildren().clear();
        labelOptionTitle.setText("");

        optionViewModel = newOptionViewModel;
        bindOptionViewModel();
        addRows();
    }

    private void addRows() {
        if (optionViewModel == null) {
            return;
        }

        if (optionViewModel.timeProperty() != null) addRow(labelTime, textFieldTime);
        if (optionViewModel.placeProperty() != null) addRow(labelPlace, choiceBoxPlace);
        if (optionViewModel.transitionProperty() != null) addRow(labelTransition, choiceBoxTransition);
        if (optionViewModel.variableProperty() != null) addRow(labelVariable, choiceBoxVariable);
        if (optionViewModel.functionProperty() != null) addRow(labelFunction, choiceBoxFunction);
    }

    private void addRow(Node left, Node right) {
        gridPaneProperties.addRow(gridPaneProperties.getRowCount(), left, right);
    }

    private void bindOptionViewModel() {
        if (optionViewModel == null) {
            return;
        }
        labelOptionTitle.textProperty().bind(optionViewModel.titleProperty());
        if (optionViewModel.timeProperty() != null)
            textFieldTime.textProperty().bindBidirectional(optionViewModel.timeProperty());
        if (optionViewModel.placeProperty() != null)
            choiceBoxPlace.valueProperty().bindBidirectional(optionViewModel.placeProperty());
        if (optionViewModel.transitionProperty() != null)
            choiceBoxTransition.valueProperty().bindBidirectional(optionViewModel.transitionProperty());
        if (optionViewModel.variableProperty() != null)
            choiceBoxVariable.valueProperty().bindBidirectional(optionViewModel.variableProperty());
        if (optionViewModel.functionProperty() != null)
            choiceBoxFunction.valueProperty().bindBidirectional(optionViewModel.functionProperty());
    }

    private void unbindOptionViewModel() {
        if (optionViewModel == null) {
            return;
        }
        labelOptionTitle.textProperty().unbind();
        if (optionViewModel.timeProperty() != null)
            textFieldTime.textProperty().unbindBidirectional(optionViewModel.timeProperty());
        if (optionViewModel.placeProperty() != null)
            choiceBoxPlace.valueProperty().unbindBidirectional(optionViewModel.placeProperty());
        if (optionViewModel.transitionProperty() != null)
            choiceBoxTransition.valueProperty().unbindBidirectional(optionViewModel.transitionProperty());
        if (optionViewModel.variableProperty() != null)
            choiceBoxVariable.valueProperty().unbindBidirectional(optionViewModel.variableProperty());
        if (optionViewModel.functionProperty() != null)
            choiceBoxFunction.valueProperty().unbindBidirectional(optionViewModel.functionProperty());
    }

}
