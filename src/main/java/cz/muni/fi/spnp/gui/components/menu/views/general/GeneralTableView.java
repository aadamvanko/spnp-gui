package cz.muni.fi.spnp.gui.components.menu.views.general;

import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class GeneralTableView<TViewModel> extends UIWindowComponent {

    protected final TableView<TViewModel> tableView;
    protected DiagramViewModel diagramViewModel;
    protected Button buttonAdd;
    protected Button buttonEdit;
    protected Button buttonDelete;
    protected Button buttonExit;

    public GeneralTableView(String title) {
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

    protected void addColumn(String name, String propertyName) {
        TableColumn<TViewModel, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        tableView.getColumns().add(column);
    }
}
