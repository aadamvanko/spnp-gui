package cz.muni.fi.spnp.gui.components.menu.views.diagrams;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class NewDiagramView extends UIWindowComponent {
    private final Model model;
    private final Notifications notifications;
    private final ChoiceBox<ProjectViewModel> choiceBoxProject;

    public NewDiagramView(Model model, Notifications notifications) {
        this.model = model;
        this.notifications = notifications;

        var vbox = new VBox();
        var gridPane = new GridPane();

        var labelName = new Label("Name");
        gridPane.add(labelName, 0, 0);
        var textFieldName = new TextField();
        gridPane.add(textFieldName, 1, 0);
        var labelProject = new Label("Project");
        gridPane.add(labelProject, 0, 1);
        choiceBoxProject = new ChoiceBox<ProjectViewModel>();
        choiceBoxProject.setConverter(new ProjectViewModelStringConverter(model));
        choiceBoxProject.setItems(model.getProjects());
        gridPane.add(choiceBoxProject, 1, 1);
        vbox.getChildren().add(gridPane);

        var buttonsPanel = new HBox();
        var buttonCreate = new Button("Create");
        buttonCreate.setOnMouseClicked(mouseEvent -> {
            var name = textFieldName.getText();
            if (name.isBlank()) {
                DialogMessages.showError("Diagram name cannot be blank.");
                return;
            }

            var project = choiceBoxProject.getSelectionModel().getSelectedItem();
            if (project.diagramExists(name)) {
                DialogMessages.showError("Diagram with given name already exists.");
                return;
            }

            var diagram = new DiagramViewModel(notifications, project);
            diagram.nameProperty().set(name);
            project.addDiagram(diagram);
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCreate);
        var buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCancel);
        vbox.getChildren().add(buttonsPanel);

        stage.setTitle("New Diagram");
        stage.setScene(new Scene(vbox));
    }

    public void prepare() {
        if (model.getSelectedProject() != null) {
            choiceBoxProject.getSelectionModel().select(model.getSelectedProject());
        } else if (!model.getProjects().isEmpty()) {
            choiceBoxProject.getSelectionModel().select(0);
        }
    }
}
