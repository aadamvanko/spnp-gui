package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FunctionsView extends UIWindowComponent {

    private final ListView<FunctionViewModel> listViewNames;
    private final TextArea textAreaBody;
    private final FunctionView functionView;
    private DiagramViewModel diagramViewModel;

    public FunctionsView() {
        functionView = new FunctionView();
        listViewNames = new ListView<>();
        listViewNames.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewNames.setPlaceholder(new Label("No functions to show"));
        listViewNames.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(FunctionViewModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.nameProperty() == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s", item.nameProperty().get(), item.functionTypeProperty().get()));
                }
            }
        });

        textAreaBody = new TextArea();
        HBox.setHgrow(textAreaBody, Priority.ALWAYS);
        textAreaBody.setEditable(false);

        listViewNames.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                textAreaBody.setText("");
            } else {
                textAreaBody.setText(newValue.bodyProperty().get());
            }
        });

        var splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().add(listViewNames);
        splitPane.getItems().add(textAreaBody);
        splitPane.setDividerPositions(0.3);

        var buttonsPanel = new HBox();
        buttonsPanel.setSpacing(5);
        var buttonAdd = new Button("Add");
        buttonAdd.setOnMouseClicked(mouseEvent -> {
            functionView.bindDiagramViewModel(diagramViewModel);
            functionView.getStage().setTitle("Add Function");
            functionView.getStage().showAndWait();
        });
        buttonsPanel.getChildren().add(buttonAdd);
        var buttonDelete = new Button("Delete");
        buttonDelete.setOnMouseClicked(mouseEvent -> {
            var selectedItem = listViewNames.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            if (selectedItem.isRequired()) {
                DialogMessages.showError("Cannot delete predefined function, only edit it!");
                return;
            }
            diagramViewModel.getFunctions().remove(selectedItem);
        });
        buttonsPanel.getChildren().add(buttonDelete);
        var buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(mouseEvent -> stage.close());
        buttonsPanel.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);
        vbox.getChildren().add(splitPane);
        vbox.getChildren().add(buttonsPanel);
        stage.setTitle("Functions");
        stage.setScene(new Scene(vbox));
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        listViewNames.setItems(diagramViewModel.getFunctions());
        if (!diagramViewModel.getFunctions().isEmpty()) {
            listViewNames.getSelectionModel().select(0);
        }
    }
}
