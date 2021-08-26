package cz.muni.fi.spnp.gui.components.menu.views.diagrams;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NewDiagramView extends UIWindowComponent {

    private final Model model;
    private ChoiceBox<ProjectViewModel> choiceBoxProject;

    public NewDiagramView(Model model) {
        this.model = model;

        createView();

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
        model.getProjects().addListener(this::onProjectsChangedListener);
    }

    private void createView() {
        var vbox = new VBox();
        var gridPane = new GridPane();

        var labelName = new Label("Name:");
        gridPane.add(labelName, 0, 0);
        var textFieldName = new TextField();
        GridPane.setHgrow(textFieldName, Priority.ALWAYS);
        gridPane.add(textFieldName, 1, 0);
        var labelProject = new Label("Project:");
        gridPane.add(labelProject, 0, 1);
        choiceBoxProject = new ChoiceBox<>();
        choiceBoxProject.setConverter(new ProjectViewModelStringConverter(model));
        choiceBoxProject.setItems(model.getProjects());
        GridPane.setHgrow(choiceBoxProject, Priority.ALWAYS);
        choiceBoxProject.minWidthProperty().bind(textFieldName.widthProperty());
        gridPane.add(choiceBoxProject, 1, 1);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
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

            var diagram = new DiagramViewModel(project);
            diagram.nameProperty().set(name);
            project.getDiagrams().add(diagram);
            model.selectedDiagramProperty().set(diagram);
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCreate);
        var buttonCancel = new Button("Cancel");
        buttonCancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonCancel);
        buttonsPanel.setSpacing(5);

        vbox.getChildren().add(buttonsPanel);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);

        stage.setTitle("New Diagram");
        stage.setScene(new Scene(vbox));
        stage.setMinWidth(250);
        stage.setResizable(false);
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (newDiagram == null) {
            if (!model.getProjects().isEmpty()) {
                choiceBoxProject.getSelectionModel().select(model.getProjects().get(0));
            }
        } else {
            choiceBoxProject.getSelectionModel().select(newDiagram.getProject());
        }
    }

    private void onProjectsChangedListener(ListChangeListener.Change<? extends ProjectViewModel> projectsChange) {
        if (model.selectedDiagramProperty().get() == null) {
            if (model.getProjects().isEmpty()) {
                choiceBoxProject.getSelectionModel().clearSelection();
            } else {
                choiceBoxProject.getSelectionModel().select(model.getProjects().get(0));
            }
        }
    }

}
