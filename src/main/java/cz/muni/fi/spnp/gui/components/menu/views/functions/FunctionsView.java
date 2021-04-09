package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FunctionsView extends UIWindowComponent {
    private final ListView<Function> listViewNames;
    private final TextArea textAreaBody;
    private final FunctionView functionView;
    private DiagramViewModel diagramViewModel;

    public FunctionsView() {
        functionView = new FunctionView();
        listViewNames = new ListView<>();
        listViewNames.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewNames.setPlaceholder(new Label("No functions to show"));
        listViewNames.setCellFactory(param -> new ListCell<Function>() {
            @Override
            protected void updateItem(Function item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(String.format("%s - %s", item.getName(), item.getFunctionType()));
                }
            }
        });

        textAreaBody = new TextArea();
        textAreaBody.setEditable(false);

        listViewNames.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                textAreaBody.setText("");
            } else {
                textAreaBody.setText(newValue.getBody());
            }
        });

        var hbox = new HBox();
        hbox.getChildren().add(listViewNames);
        hbox.getChildren().add(textAreaBody);

        var buttonsPanel = new HBox();
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
            diagramViewModel.getFunctions().remove(selectedItem);
        });
        buttonsPanel.getChildren().add(buttonDelete);
        var buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        buttonsPanel.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.getChildren().add(hbox);
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
