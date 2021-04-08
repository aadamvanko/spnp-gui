package cz.muni.fi.spnp.gui.components.menu.views.define;

import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
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

public class DefinesView extends UIWindowComponent {
    private final TableView<Define> tableView;
    private final DefineView defineView;
    private DiagramViewModel diagramViewModel;

    public DefinesView() {
        tableView = new TableView<>();
        defineView = new DefineView();

        TableColumn<Define, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Define, String> expressionColumn = new TableColumn<>("Expression");
        expressionColumn.setCellValueFactory(new PropertyValueFactory<>("expression"));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(expressionColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//
//        tableView.getItems().add(new Define("MAX_SIZE", "10"));
//        tableView.getItems().add(new Define("MIN_SIZE", "2"));

        var buttonsHbox = new HBox();

        var buttonAdd = new Button("Add");
        buttonAdd.setOnMouseClicked(mouseEvent -> {
            defineView.bindDiagramViewModel(this.diagramViewModel);
            defineView.getStage().setTitle("Add Define");
            defineView.getStage().showAndWait();
        });
        buttonsHbox.getChildren().add(buttonAdd);
        var buttonDelete = new Button("Delete");
        buttonDelete.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            diagramViewModel.getDefines().remove(selectedItem);
        });
        buttonsHbox.getChildren().add(buttonDelete);
        var buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        buttonsHbox.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.getChildren().add(tableView);
        vbox.getChildren().add(buttonsHbox);

        var scene = new Scene(vbox);
        stage.setScene(scene);
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        tableView.setItems(diagramViewModel.getDefines());
    }

    public void setDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }
}
