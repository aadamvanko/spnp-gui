package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FunctionsView extends UIWindowComponent {

    private final ViewModelCopyFactory viewModelCopyFactory;
    private final ListView<FunctionViewModel> listViewNames;
    private final TextArea textAreaBody;
    private DiagramViewModel diagramViewModel;

    public FunctionsView() {
        this.viewModelCopyFactory = new ViewModelCopyFactory();

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
            var functionView = new FunctionView(diagramViewModel, new FunctionViewModel(), null, ItemViewMode.ADD);
            functionView.getStage().setTitle("Add Function");
            functionView.getStage().showAndWait();
        });
        buttonsPanel.getChildren().add(buttonAdd);

        var buttonEdit = new Button("Edit");
        buttonEdit.setOnMouseClicked(mouseEvent -> {
            var selectedFunction = listViewNames.getSelectionModel().getSelectedItem();
            if (selectedFunction == null) {
                return;
            }

            var copy = viewModelCopyFactory.createCopy(selectedFunction);
            var functionView = new FunctionView(diagramViewModel, copy, selectedFunction, ItemViewMode.EDIT);
            functionView.getStage().setTitle("Edit Function");
            functionView.getStage().showAndWait();
        });
        buttonsPanel.getChildren().add(buttonEdit);

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

    public void unbindDiagramViewModel() {
        this.diagramViewModel = null;
        listViewNames.setItems(FXCollections.emptyObservableList());
    }

}
