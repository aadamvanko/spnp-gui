package cz.muni.fi.spnp.gui.components.menu.views.general;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class GeneralItemsTableView<TViewModel> {

    protected final Model model;
    protected final ViewModelCopyFactory viewModelCopyFactory;
    protected final TableView<TViewModel> tableView;
    protected ObservableList<TViewModel> sourceCollection;

    public GeneralItemsTableView(Model model) {
        this.model = model;
        this.viewModelCopyFactory = new ViewModelCopyFactory();
        this.tableView = new TableView<>();

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        tableView.setRowFactory(tv -> {
            TableRow<TViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    if (row.isEmpty()) {
                        var itemView = createItemViewAdd(createViewModel());
                        itemView.setSourceCollection(sourceCollection);
                        itemView.getStage().setTitle("Add row");
                        itemView.getStage().showAndWait();
                    } else {
                        TViewModel rowItem = row.getItem();
                        var itemView = createItemViewEdit(rowItem);
                        itemView.setSourceCollection(sourceCollection);
                        itemView.getStage().setTitle("Edit row");
                        itemView.getStage().showAndWait();
                    }
                }
            });
            return row;
        });
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
