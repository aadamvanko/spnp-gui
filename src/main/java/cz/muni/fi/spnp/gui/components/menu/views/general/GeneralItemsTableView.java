package cz.muni.fi.spnp.gui.components.menu.views.general;

import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class GeneralItemsTableView<TViewModel> extends UIWindowComponent {

    protected final TableView<TViewModel> tableView;
    protected ObservableList<TViewModel> sourceCollection;
    protected Button buttonAdd;
    protected Button buttonEdit;
    protected Button buttonDelete;
    protected Button buttonExit;

    public GeneralItemsTableView(String title, String entityName) {
        this.tableView = new TableView<>();

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        var buttonsHbox = new HBox();

        buttonAdd = new Button("Add");
        buttonsHbox.getChildren().add(buttonAdd);

        buttonEdit = new Button("Edit");
        buttonsHbox.getChildren().add(buttonEdit);

        buttonDelete = new Button("Delete");
        buttonsHbox.getChildren().add(buttonDelete);

        buttonAdd.setOnMouseClicked(mouseEvent -> {
            var itemView = createItemViewAdd(createViewModel());
            itemView.setSourceCollection(sourceCollection);
            itemView.getStage().setTitle("Add " + entityName);
            itemView.getStage().showAndWait();
        });

        buttonEdit.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            var itemView = createItemViewEdit(selectedItem);
            itemView.setSourceCollection(sourceCollection);
            itemView.getStage().setTitle("Edit " + entityName);
            itemView.getStage().showAndWait();
        });

        buttonDelete.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            sourceCollection.remove(selectedItem);
        });

        buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        buttonsHbox.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.getChildren().add(tableView);
        vbox.getChildren().add(buttonsHbox);

        var scene = new Scene(vbox);
        stage.setTitle(title);
        stage.setScene(scene);
    }

    public void bindSourceCollection(ObservableList<TViewModel> sourceCollection) {
        this.sourceCollection = sourceCollection;
        this.tableView.setItems(sourceCollection);
    }

    protected abstract TViewModel createViewModel();

    protected abstract GeneralItemView<TViewModel> createItemViewAdd(TViewModel viewModel);

    protected abstract GeneralItemView<TViewModel> createItemViewEdit(TViewModel viewModel);

    protected void addColumn(String name, String propertyName) {
        TableColumn<TViewModel, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        tableView.getColumns().add(column);
    }
}
