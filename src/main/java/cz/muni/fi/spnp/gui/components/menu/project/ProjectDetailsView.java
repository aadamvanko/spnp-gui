package cz.muni.fi.spnp.gui.components.menu.project;

import cz.muni.fi.spnp.gui.components.common.DialogMessages;
import cz.muni.fi.spnp.gui.components.common.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
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

/**
 * Window for the creation of a new project and its editing.
 */
public class ProjectDetailsView extends UIWindowComponent {

    private final Model model;
    private final ProjectViewModel projectViewModel;
    private final ItemViewMode itemViewMode;

    public ProjectDetailsView(Model model, ProjectViewModel projectViewModel, ItemViewMode itemViewMode) {
        this.model = model;
        this.projectViewModel = projectViewModel;
        this.itemViewMode = itemViewMode;

        createView();
    }

    private void createView() {
        var vbox = new VBox();
        var gridPane = new GridPane();

        var labelName = new Label("Name:");
        gridPane.add(labelName, 0, 0);
        var textFieldName = new TextField();
        GridPane.setHgrow(textFieldName, Priority.ALWAYS);
        gridPane.add(textFieldName, 1, 0);
        vbox.getChildren().add(gridPane);
        gridPane.setHgap(5);

        if (itemViewMode == ItemViewMode.EDIT) {
            textFieldName.setText(projectViewModel.getName());
        }

        var buttonsPanel = new HBox();
        var buttonOK = new Button("OK");
        buttonOK.setOnAction(actionEvent -> {
            var name = textFieldName.getText();
            if (name.isBlank()) {
                DialogMessages.showError("Project name cannot be blank.");
                return;
            }

            if (name.chars().anyMatch(Character::isWhitespace)) {
                DialogMessages.showError("Project name cannot contain whitespace characters.");
                return;
            }

            if (itemViewMode == ItemViewMode.ADD) {
                var newProject = new ProjectViewModel();
                newProject.nameProperty().set(name);
                model.getProjects().add(newProject);
            } else if (itemViewMode == ItemViewMode.EDIT) {
                projectViewModel.nameProperty().set(name);
            }

            stage.close();
        });
        buttonsPanel.getChildren().add(buttonOK);
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

        var titleMode = itemViewMode == ItemViewMode.ADD ? "New" : "Edit";
        stage.setTitle(titleMode + " Project");
        stage.setScene(scene);
        stage.setMinWidth(250);
        stage.setResizable(false);
    }

}
