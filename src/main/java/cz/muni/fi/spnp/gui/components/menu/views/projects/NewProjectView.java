package cz.muni.fi.spnp.gui.components.menu.views.projects;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewProjectView extends UIWindowComponent {
    private final Model model;

    public NewProjectView(Model model, Notifications notifications) {
        this.model = model;

        var vbox = new VBox();
        var gridPane = new GridPane();

        var labelName = new Label("Name");
        gridPane.add(labelName, 0, 0);
        var textFieldName = new TextField();
        gridPane.add(textFieldName, 1, 0);
        vbox.getChildren().add(gridPane);

        var buttonsPanel = new HBox();
        var buttonCreate = new Button("Create");
        buttonCreate.setOnMouseClicked(mouseEvent -> {
            var name = textFieldName.getText();
            if (name.isBlank()) {
                DialogMessages.showError("Project name cannot be blank.");
                return;
            }

            if (model.projectExists(name)) {
                DialogMessages.showError("Project with given name already exists.");
                return;
            }

            var project = new ProjectViewModel(notifications);
            project.nameProperty().set(name);
            model.getProjects().add(project);
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCreate);
        var buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCancel);
        vbox.getChildren().add(buttonsPanel);

        stage.setTitle("New Project");
        stage.setScene(new Scene(vbox));
    }


}
