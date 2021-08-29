package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.storing.OldFormatUtils;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FunctionView extends UIWindowComponent {

    private final ViewModelCopyFactory viewModelCopyFactory;
    private DiagramViewModel diagramViewModel;
    private final Map<FunctionType, ObservableList<FunctionReturnType>> possibleReturnsTypes;
    private final ChangeListener<FunctionType> onTypeChangedListener;

    private final ChoiceBox<FunctionReturnType> choiceBoxReturnType;

    public FunctionView(DiagramViewModel diagramViewModel, FunctionViewModel viewModel, FunctionViewModel original, ItemViewMode itemViewMode) {
        this.viewModelCopyFactory = new ViewModelCopyFactory();
        this.diagramViewModel = diagramViewModel;
        this.onTypeChangedListener = this::onTypeChangedListener;

        possibleReturnsTypes = new HashMap<>();
        possibleReturnsTypes.put(FunctionType.Generic, FXCollections.observableArrayList(FunctionReturnType.values()));
        possibleReturnsTypes.put(FunctionType.Guard, FXCollections.observableArrayList(FunctionReturnType.INT));
        possibleReturnsTypes.put(FunctionType.Reward, FXCollections.observableArrayList(FunctionReturnType.DOUBLE));
        possibleReturnsTypes.put(FunctionType.ArcCardinality, FXCollections.observableArrayList(FunctionReturnType.INT));
        possibleReturnsTypes.put(FunctionType.Probability, FXCollections.observableArrayList(FunctionReturnType.DOUBLE));
        possibleReturnsTypes.put(FunctionType.Distribution, FXCollections.observableArrayList(FunctionReturnType.DOUBLE));
        possibleReturnsTypes.put(FunctionType.Halting, FXCollections.observableArrayList(FunctionReturnType.DOUBLE));
        possibleReturnsTypes.put(FunctionType.Other, FXCollections.observableArrayList(FunctionReturnType.values()));

        var nameTextField = new TextField();
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
        nameTextField.setPrefWidth(200);
        var choiceBoxType = new ChoiceBox<FunctionType>();
        var functionTypes = FXCollections.observableArrayList(FunctionType.values());
        choiceBoxType.setItems(functionTypes);
        choiceBoxType.valueProperty().bindBidirectional(viewModel.functionTypeProperty());
        choiceBoxType.prefWidthProperty().bind(nameTextField.widthProperty());

        choiceBoxReturnType = new ChoiceBox<>();
        choiceBoxReturnType.setItems(possibleReturnsTypes.get(viewModel.getFunctionType()));
        choiceBoxReturnType.valueProperty().bindBidirectional(viewModel.returnTypeProperty());
        choiceBoxReturnType.prefWidthProperty().bind(nameTextField.widthProperty());

        viewModel.functionTypeProperty().addListener(this.onTypeChangedListener);

        if (viewModel.isRequired()) {
            nameTextField.setDisable(true);
            choiceBoxType.setDisable(true);
            choiceBoxReturnType.setDisable(true);
        }

        if (itemViewMode == ItemViewMode.EDIT) {
            choiceBoxType.setDisable(true);
        }

        var gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(new Label("Type:"), 0, 1);
        gridPane.add(choiceBoxType, 1, 1);
        gridPane.add(new Label("Return type:"), 0, 2);
        gridPane.add(choiceBoxReturnType, 1, 2);

        var textAreaDefinition = new TextArea();
        VBox.setVgrow(textAreaDefinition, Priority.ALWAYS);
        textAreaDefinition.setPromptText("Definition:");
        textAreaDefinition.textProperty().bindBidirectional(viewModel.bodyProperty());
        textAreaDefinition.setEditable(viewModel.isVisible());

        var buttonsPanel = new HBox();
        buttonsPanel.setSpacing(5);
        var buttonOk = new Button("Ok");
        buttonOk.setOnMouseClicked(mouseEvent -> {
            var forbiddenNames = Set.of("options", "net");
            if (viewModel.getName().isBlank()) {
                DialogMessages.showError("Field name cannot be blank!");
                return;
            } else if (viewModel.getName().equals(OldFormatUtils.NULL_VALUE)) {
                DialogMessages.showError("String null is not valid function name due to the old format's problems.");
                return;
            } else if (forbiddenNames.contains(viewModel.getName())) {
                DialogMessages.showError("This name is reserved for internal CSPL functions.");
                return;
            }

            if (itemViewMode == ItemViewMode.ADD) {
                if (this.diagramViewModel.getFunctions().contains(viewModel)) {
                    DialogMessages.showError("Conflicting name!");
                    return;
                }
                diagramViewModel.getFunctions().add(viewModel);
            } else if (itemViewMode == ItemViewMode.EDIT) {
                var foundItems = this.diagramViewModel.getFunctions().stream()
                        .filter(fvm -> fvm.equals(viewModel)).collect(Collectors.toList());
                if (foundItems.size() >= 2) {
                    throw new AssertionError("Collection cannot contain 2+ of the same object!");
                }

                if (!foundItems.isEmpty() && foundItems.get(0) != original) {
                    DialogMessages.showError("Function with the same name already exists!");
                    return;
                }

                viewModelCopyFactory.copyTo(original, viewModel);
            }

            viewModel.functionTypeProperty().removeListener(this.onTypeChangedListener);
            this.diagramViewModel = null;
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonOk);

        var buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(mouseEvent -> {
            viewModel.functionTypeProperty().removeListener(this.onTypeChangedListener);
            this.diagramViewModel = null;
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCancel);

        var vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);

        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(new Label("Body:"));
        vbox.getChildren().add(textAreaDefinition);
        vbox.getChildren().add(buttonsPanel);

        stage.setScene(new Scene(vbox));
    }

    private void onTypeChangedListener(ObservableValue<? extends FunctionType> observableValue, FunctionType oldValue, FunctionType newValue) {
        if (newValue != null) {
            choiceBoxReturnType.setItems(possibleReturnsTypes.get(newValue));
            choiceBoxReturnType.getSelectionModel().select(possibleReturnsTypes.get(newValue).get(0));
        }
    }

}
