package cz.muni.fi.spnp.gui.components.menu.views.preferences;

import cz.muni.fi.spnp.gui.ModelSaver;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class PreferencesView extends UIWindowComponent {

    private final Model model;

    private Label pathSPNPLabel;
    private TextField pathSPNPTextField;
    private Label pathSPNPExamplesLabel;
    private TextField pathSPNPExamplesTextField;
    private Label pathPlotsLibraryLabel;
    private TextField pathPlotsLibraryTextField;

    public PreferencesView(Model model) {
        this.model = model;

        createView();
        bindViewModel();
    }

    private void createView() {
        pathSPNPLabel = new Label("Path of the interface directory:");
        pathSPNPTextField = new TextField();
        pathSPNPTextField.setEditable(false);
        pathSPNPTextField.setOnAction(this::onPathSPNPTextFieldClickedHandler);

        pathSPNPExamplesLabel = new Label("Directory for the SPNP examples:");
        pathSPNPExamplesTextField = new TextField();
        pathSPNPExamplesTextField.setEditable(false);
        pathSPNPExamplesTextField.setOnAction(this::onPathExamplesTextFieldClickedHandler);

        pathPlotsLibraryLabel = new Label("Directory for the plots library:");
        pathPlotsLibraryTextField = new TextField();
        pathPlotsLibraryTextField.setEditable(false);
        pathPlotsLibraryTextField.setOnAction(this::onPlotsLibraryTextFieldClickedHandler);

        var okButton = new Button("Ok");
        HBox.setHgrow(okButton, Priority.ALWAYS);
        okButton.setMaxWidth(Double.MAX_VALUE);
        okButton.setOnAction(this::onOkButtonClickedHandler);

        var saveButton = new Button("Save");
        HBox.setHgrow(saveButton, Priority.ALWAYS);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setOnAction(this::onSaveButtonClickedHandler);

        var buttonsHbox = new HBox(okButton, saveButton);
        buttonsHbox.setSpacing(5);

        var vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);
        vbox.setPrefWidth(300);

        vbox.getChildren().add(pathSPNPLabel);
        vbox.getChildren().add(pathSPNPTextField);
        vbox.getChildren().add(pathSPNPExamplesLabel);
        vbox.getChildren().add(pathSPNPExamplesTextField);
        vbox.getChildren().add(pathPlotsLibraryLabel);
        vbox.getChildren().add(pathPlotsLibraryTextField);
        vbox.getChildren().add(buttonsHbox);
        stage.setTitle("Preferences");
        stage.setScene(new Scene(vbox));
        stage.setResizable(false);
    }

    private void onPathSPNPTextFieldClickedHandler(ActionEvent actionEvent) {
        chooseDirectory(model.pathSPNPProperty());
    }

    private void onPathExamplesTextFieldClickedHandler(ActionEvent actionEvent) {
        chooseDirectory(model.pathSPNPExamplesProperty());
    }

    private void onPlotsLibraryTextFieldClickedHandler(ActionEvent actionEvent) {
        chooseDirectory(model.pathPlotsLibraryProperty());
    }

    private void chooseDirectory(StringProperty stringProperty) {
        var directoryChooser = new DirectoryChooser();
        var file = directoryChooser.showDialog(stage);
        if (file != null) {
            stringProperty.set(file.getPath());
        }
    }

    private void onOkButtonClickedHandler(ActionEvent actionEvent) {
        stage.close();
    }

    private void onSaveButtonClickedHandler(ActionEvent actionEvent) {
        var modelSaver = new ModelSaver();
        modelSaver.savePathsSPNP(model);
    }

    private void bindViewModel() {
        pathSPNPTextField.textProperty().bind(model.pathSPNPProperty());
        pathSPNPExamplesTextField.textProperty().bind(model.pathSPNPExamplesProperty());
        pathPlotsLibraryTextField.textProperty().bind(model.pathPlotsLibraryProperty());
    }

}
