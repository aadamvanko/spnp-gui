package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class FunctionViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final ObjectProperty<FunctionType> functionType = new SimpleObjectProperty<>(FunctionType.Generic);
    private final StringProperty body = new SimpleStringProperty("");

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<FunctionType> functionTypeProperty() {
        return functionType;
    }

    public StringProperty bodyProperty() {
        return body;
    }
}

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

        var gridPane = new GridPane();
        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(nameTextField, 1, 0);
        gridPane.add(new Label("Type"), 0, 1);
        gridPane.add(choiceBoxType, 1, 1);

        var textAreaDefinition = new TextArea();
        textAreaDefinition.setPromptText("Definition");
        textAreaDefinition.textProperty().bindBidirectional(viewModel.bodyProperty());

        var buttonsPanel = new HBox();
        var buttonOk = new Button("Ok");
        buttonOk.setOnMouseClicked(mouseEvent -> {
            if (nameTextField.textProperty().get().isBlank()) {
                return;
            }

            var function = new Function(viewModel.nameProperty().get(), viewModel.functionTypeProperty().get(), viewModel.bodyProperty().get());
            if (diagramViewModel.getFunctions().contains(function)) {
                DialogMessages.showError("Conflicting name!");
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

        var vbox = new VBox();
        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(textAreaDefinition);
        vbox.getChildren().add(buttonsPanel);

        stage.setScene(new Scene(vbox));
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }
}