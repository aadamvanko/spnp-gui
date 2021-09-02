package cz.muni.fi.spnp.gui.components.menu.views.general;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class GeneralItemsTableView<TViewModel> {

    protected final Model model;
    protected final String entityName;
    protected final ViewModelCopyFactory viewModelCopyFactory;
    protected final TableView<TViewModel> tableView;
    protected ObservableList<TViewModel> sourceCollection;

    public GeneralItemsTableView(Model model, String entityName) {
        this.model = model;
        this.entityName = entityName;
        this.viewModelCopyFactory = new ViewModelCopyFactory();
        this.tableView = new TableView<>();

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        VBox.setVgrow(tableView, Priority.ALWAYS);


        var placeholderLabel = new Label("No content in table");
        var placeholderVBox = new VBox(placeholderLabel);
        placeholderVBox.setAlignment(Pos.CENTER);
        placeholderVBox.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                showAddRowView();
            }
        });
        tableView.setPlaceholder(placeholderVBox);

        tableView.setRowFactory(tv -> {
            TableRow<TViewModel> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (row.isEmpty()) {
                        showAddRowView();
                    } else {
                        TViewModel rowItem = row.getItem();
                        showEditRowView(rowItem);
                    }
                }
            });
            return row;
        });
    }

    public void showAddRowView() {
        var itemView = createItemViewAdd(createViewModel());
        itemView.setSourceCollection(sourceCollection);
        itemView.getStage().setTitle("Add " + entityName);
        itemView.getStage().showAndWait();
    }

    public void showEditRowView(TViewModel item) {
        var itemView = createItemViewEdit(item);
        itemView.setSourceCollection(sourceCollection);
        itemView.getStage().setTitle("Edit " + entityName);
        itemView.getStage().showAndWait();
    }

    public void bindSourceCollection(ObservableList<TViewModel> sourceCollection) {
        this.sourceCollection = sourceCollection;
        this.tableView.setItems(sourceCollection);
    }

    public abstract TViewModel createViewModel();

    public abstract GeneralItemView<TViewModel> createItemViewAdd(TViewModel viewModel);

    public abstract GeneralItemView<TViewModel> createItemViewEdit(TViewModel viewModel);

    protected void addColumn(String name, String propertyName) {
        TableColumn<TViewModel, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        tableView.getColumns().add(column);
    }

    public TableView<TViewModel> getTableView() {
        return tableView;
    }

    public ObservableList<TViewModel> getSourceCollection() {
        return sourceCollection;
    }

}
