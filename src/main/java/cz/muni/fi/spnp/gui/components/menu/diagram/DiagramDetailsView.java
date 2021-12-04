package cz.muni.fi.spnp.gui.components.menu.diagram;

import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.menu.view.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.view.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class DiagramDetailsView extends UIWindowComponent {

    private final Model model;
    private final DiagramViewModel diagramViewModel;
    private final ItemViewMode itemViewMode;
    private final ProjectViewModel projectViewModel;
    private ChoiceBox<ProjectViewModel> choiceBoxProject;

    public DiagramDetailsView(Model model, DiagramViewModel diagramViewModel, ItemViewMode itemViewMode, ProjectViewModel projectViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;
        this.itemViewMode = itemViewMode;
        this.projectViewModel = projectViewModel;

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

        if (itemViewMode == ItemViewMode.ADD) {
            var selectedDiagram = model.selectedDiagramProperty().get();
            if (projectViewModel != null) {
                choiceBoxProject.getSelectionModel().select(projectViewModel);
            } else if (selectedDiagram == null) {
                choiceBoxProject.getSelectionModel().select(model.getProjects().get(0));
            } else {
                choiceBoxProject.getSelectionModel().select(selectedDiagram.getProject());
            }
        } else if (itemViewMode == ItemViewMode.EDIT) {
            textFieldName.setText(diagramViewModel.getName());
            choiceBoxProject.setDisable(true);
            choiceBoxProject.getSelectionModel().select(diagramViewModel.getProject());
        }

        var buttonsPanel = new HBox();
        var buttonOK = new Button("OK");
        buttonOK.setOnAction(actionEvent -> {
            var name = textFieldName.getText();
            if (name.isBlank()) {
                DialogMessages.showError("Diagram name cannot be blank.");
                return;
            }

            if (name.chars().anyMatch(Character::isWhitespace)) {
                DialogMessages.showError("Diagram name cannot contain whitespace characters.");
                return;
            }

            var project = choiceBoxProject.getSelectionModel().getSelectedItem();
            if (itemViewMode == ItemViewMode.ADD) {
                if (project.diagramExists(name)) {
                    DialogMessages.showError("Diagram with given name already exists.");
                    return;
                }
                var diagram = new DiagramViewModel(project);
                diagram.nameProperty().set(name);
                project.getDiagrams().add(diagram);
                model.selectedDiagramProperty().set(diagram);
            } else if (itemViewMode == ItemViewMode.EDIT) {
                var foundDiagrams = project.getDiagrams().stream()
                        .filter(d -> d.getName().equals(name))
                        .collect(Collectors.toList());
                if (foundDiagrams.size() == 1 && foundDiagrams.get(0) != diagramViewModel) {
                    if (project.diagramExists(name)) {
                        DialogMessages.showError("Diagram with given name already exists.");
                        return;
                    }
                }
                diagramViewModel.nameProperty().set(name);
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
        stage.setTitle(titleMode + " Diagram");
        stage.setScene(scene);
        stage.setMinWidth(250);
        stage.setResizable(false);
    }

}
