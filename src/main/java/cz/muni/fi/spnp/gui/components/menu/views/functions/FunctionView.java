package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.storing.OldFormatUtils;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FunctionView extends UIWindowComponent {

    private final FunctionViewModel viewModel = new FunctionViewModel();
    private DiagramViewModel diagramViewModel;

    public FunctionView() {
        var nameTextField = new TextField();
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
        var choiceBoxType = new ChoiceBox<FunctionType>();
        var functionTypes = FXCollections.observableArrayList(FunctionType.values());
        choiceBoxType.setItems(functionTypes);
        choiceBoxType.valueProperty().bindBidirectional(viewModel.functionTypeProperty());
        choiceBoxType.prefWidthProperty().bind(nameTextField.widthProperty());

        var choiceBoxReturnType = new ChoiceBox<FunctionReturnType>();
        var functionReturnTypes = FXCollections.observableArrayList(FunctionReturnType.values());
        choiceBoxReturnType.setItems(functionReturnTypes);
        choiceBoxReturnType.valueProperty().bindBidirectional(viewModel.returnTypeProperty());
        choiceBoxReturnType.prefWidthProperty().bind(nameTextField.widthProperty());

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

        var buttonsPanel = new HBox();
        buttonsPanel.setSpacing(5);
        var buttonOk = new Button("Ok");
        buttonOk.setOnMouseClicked(mouseEvent -> {
            if (nameTextField.textProperty().get().isBlank()) {
                return;
            }

            var function = new FunctionViewModel(viewModel.nameProperty().get(), viewModel.functionTypeProperty().get(),
                    viewModel.bodyProperty().get(), viewModel.returnTypeProperty().get(), false, true);
            if (diagramViewModel.getFunctions().contains(function)) {
                DialogMessages.showError("Conflicting name!");
                return;
            } else if (function.getName().equals(OldFormatUtils.NULL_VALUE)) {
                DialogMessages.showError("String null is not valid function name due to the old format's problems.");
                return;
            } else {
                diagramViewModel.getFunctions().add(function);
            }
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonOk);

        var buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(mouseEvent -> {
            diagramViewModel = null;
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCancel);

        var vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);

        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(textAreaDefinition);
        vbox.getChildren().add(buttonsPanel);

        stage.setScene(new Scene(vbox));
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }
}
