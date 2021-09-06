package cz.muni.fi.spnp.gui.components.menu.views.projects;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NewProjectView extends UIWindowComponent {

    private final Model model;

    public NewProjectView(Model model) {
        this.model = model;

        var vbox = new VBox();
        var gridPane = new GridPane();

        var labelName = new Label("Name:");
        gridPane.add(labelName, 0, 0);
        var textFieldName = new TextField();
        GridPane.setHgrow(textFieldName, Priority.ALWAYS);
        gridPane.add(textFieldName, 1, 0);
        vbox.getChildren().add(gridPane);
        gridPane.setHgap(5);

        var buttonsPanel = new HBox();
        var buttonCreate = new Button("Create");
        buttonCreate.setOnAction(actionEvent -> {
            var name = textFieldName.getText();
            if (name.isBlank()) {
                DialogMessages.showError("Project name cannot be blank.");
                return;
            }

            if (model.projectExists(name)) {
                DialogMessages.showError("Project with given name already exists.");
                return;
            }

            var project = new ProjectViewModel();
            project.nameProperty().set(name);
            model.getProjects().add(project);
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCreate);
        var buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(actionEvent -> {
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCancel);
        buttonsPanel.setSpacing(5);

        vbox.getChildren().add(buttonsPanel);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);

        var scene = new Scene(vbox);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        stage.setTitle("New Project");
        stage.setScene(scene);
        stage.setMinWidth(250);
        stage.setResizable(false);
    }


}
