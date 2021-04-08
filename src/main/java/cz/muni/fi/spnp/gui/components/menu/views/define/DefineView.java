package cz.muni.fi.spnp.gui.components.menu.views.define;

import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DefineView extends UIWindowComponent {
    private final Label nameLabel;
    private final TextField nameTextField;
    private final Label expressionLabel;
    private final TextField expressionTextField;
    private final DefineViewModel viewModel;
    private DiagramViewModel diagramViewModel;

    public DefineView() {
        var gridPane = new GridPane();
        viewModel = new DefineViewModel();

        nameLabel = new Label("Name:");
        gridPane.add(nameLabel, 0, 0);

        nameTextField = new TextField();
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
        gridPane.add(nameTextField, 1, 0);

        expressionLabel = new Label("Expression:");
        gridPane.add(expressionLabel, 0, 1);

        expressionTextField = new TextField();
        expressionTextField.textProperty().bindBidirectional(viewModel.expressionProperty());
        gridPane.add(expressionTextField, 1, 1);

        var buttonsPanel = new HBox();
        var buttonOk = new Button("Ok");
        buttonOk.setOnMouseClicked(mouseEvent -> {
            if (nameTextField.textProperty().get().isBlank()) {
                return;
            }

            if (expressionTextField.textProperty().get().isBlank()) {
                return;
            }

            var define = new Define(viewModel.nameProperty().get(), viewModel.expressionProperty().get());
            if (diagramViewModel.getDefines().contains(define)) {
                DialogMessages.showError("Conflicting name!");
                return;
            } else {
                diagramViewModel.getDefines().add(define);
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
        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(buttonsPanel);

        var scene = new Scene(vbox);
        stage.setScene(scene);
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }
}
