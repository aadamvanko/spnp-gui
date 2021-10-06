package cz.muni.fi.spnp.gui.components.menu.view.functions;

import cz.muni.fi.spnp.gui.components.menu.view.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.view.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FunctionsView extends UIWindowComponent {

    private final ViewModelCopyFactory viewModelCopyFactory;
    private final TableView<FunctionViewModel> tableView;
    private final TextArea textAreaBody;
    private DiagramViewModel diagramViewModel;

    public FunctionsView() {
        this.viewModelCopyFactory = new ViewModelCopyFactory();

        this.tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        addColumn("Name", "name");
        addColumn("Type", "functionType");
        addColumn("Return Type", "returnType");

        textAreaBody = new TextArea();
        HBox.setHgrow(textAreaBody, Priority.ALWAYS);
        VBox.setVgrow(textAreaBody, Priority.ALWAYS);
        textAreaBody.setEditable(false);

        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                textAreaBody.setText("");
            } else {
                textAreaBody.setText(newValue.bodyProperty().get());
            }
        });

        tableView.setRowFactory(tv -> {
            TableRow<FunctionViewModel> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (row.isEmpty()) {
                        showAddRowView();
                    } else {
                        FunctionViewModel rowItem = row.getItem();
                        showEditRowView(rowItem);
                    }
                }
            });
            return row;
        });

        var splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().add(tableView);
        splitPane.getItems().add(textAreaBody);
        splitPane.setDividerPositions(0.6);

        var buttonsPanel = new HBox();
        buttonsPanel.setSpacing(5);

        var buttonAdd = new Button("Add");
        buttonAdd.setOnAction(actionEvent -> {
            showAddRowView();
        });
        buttonsPanel.getChildren().add(buttonAdd);

        var buttonEdit = new Button("Edit");
        buttonEdit.setOnAction(actionEvent -> {
            var selectedFunction = tableView.getSelectionModel().getSelectedItem();
            if (selectedFunction == null) {
                return;
            }

            showEditRowView(selectedFunction);
        });
        buttonsPanel.getChildren().add(buttonEdit);

        var buttonDelete = new Button("Delete");
        buttonDelete.setOnAction(actionEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            if (selectedItem.isRequired()) {
                DialogMessages.showError("Cannot delete predefined function, only edit it!");
                return;
            }
            if (!selectedItem.isVisible()) {
                DialogMessages.showError("Cannot delete generated function!");
                return;
            }
            diagramViewModel.getFunctions().remove(selectedItem);
        });
        buttonsPanel.getChildren().add(buttonDelete);

        var buttonExit = new Button("Exit");
        buttonExit.setOnAction(actionEvent -> stage.close());
        buttonsPanel.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);
        vbox.getChildren().add(splitPane);
        vbox.getChildren().add(buttonsPanel);
        stage.setTitle("Functions");
        stage.setWidth(700);

        var scene = new Scene(vbox);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        stage.setScene(scene);
    }

    private void showAddRowView() {
        var functionView = new FunctionView(diagramViewModel, new FunctionViewModel(), null, ItemViewMode.ADD);
        functionView.getStage().setTitle("Add Function");
        functionView.getStage().showAndWait();
    }

    private void showEditRowView(FunctionViewModel rowItem) {
        var copy = viewModelCopyFactory.createCopy(rowItem);
        var functionView = new FunctionView(diagramViewModel, copy, rowItem, ItemViewMode.EDIT);
        functionView.getStage().setTitle("Edit Function");
        functionView.getStage().showAndWait();
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        tableView.setItems(diagramViewModel.getFunctions());
        if (!diagramViewModel.getFunctions().isEmpty()) {
            tableView.getSelectionModel().select(0);
        }
    }

    public void unbindDiagramViewModel() {
        this.diagramViewModel = null;
        tableView.setItems(FXCollections.emptyObservableList());
    }

    private void addColumn(String name, String propertyName) {
        TableColumn<FunctionViewModel, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        tableView.getColumns().add(column);
    }

}
