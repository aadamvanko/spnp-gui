package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

        var splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().add(tableView);
        splitPane.getItems().add(textAreaBody);
        splitPane.setDividerPositions(0.3);

        var buttonsPanel = new HBox();
        buttonsPanel.setSpacing(5);

        var buttonAdd = new Button("Add");
        buttonAdd.setOnAction(actionEvent -> {
            var functionView = new FunctionView(diagramViewModel, new FunctionViewModel(), null, ItemViewMode.ADD);
            functionView.getStage().setTitle("Add Function");
            functionView.getStage().showAndWait();
        });
        buttonsPanel.getChildren().add(buttonAdd);

        var buttonEdit = new Button("Edit");
        buttonEdit.setOnAction(actionEvent -> {
            var selectedFunction = tableView.getSelectionModel().getSelectedItem();
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
        buttonDelete.setOnAction(actionEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
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
        buttonExit.setOnAction(actionEvent -> stage.close());
        buttonsPanel.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);
        vbox.getChildren().add(splitPane);
        vbox.getChildren().add(buttonsPanel);
        stage.setTitle("Functions");
        stage.setWidth(700);
        stage.setScene(new Scene(vbox));
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
