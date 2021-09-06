package cz.muni.fi.spnp.gui.components.menu.views.general;

import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelCopyFactory;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class GeneralItemsTableViewWindow<TViewModel> extends UIWindowComponent {

    protected final Model model;
    protected final ViewModelCopyFactory viewModelCopyFactory;
    protected final GeneralItemsTableView<TViewModel> generalItemsTableView;
    protected Button buttonAdd;
    protected Button buttonEdit;
    protected Button buttonDelete;
    protected Button buttonExit;

    public GeneralItemsTableViewWindow(Model model, String title, GeneralItemsTableView<TViewModel> generalItemsTableView) {
        this.model = model;
        this.viewModelCopyFactory = new ViewModelCopyFactory();
        this.generalItemsTableView = generalItemsTableView;

        var buttonsHbox = new HBox();
        buttonsHbox.setSpacing(5);

        buttonAdd = new Button("Add");
        buttonsHbox.getChildren().add(buttonAdd);

        buttonEdit = new Button("Edit");
        buttonsHbox.getChildren().add(buttonEdit);

        buttonDelete = new Button("Delete");
        buttonsHbox.getChildren().add(buttonDelete);

        buttonAdd.setOnAction(actionEvent -> {
            generalItemsTableView.showAddRowView();
        });

        buttonEdit.setOnAction(actionEvent -> {
            var selectedItem = generalItemsTableView.getTableView().getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            generalItemsTableView.showEditRowView(selectedItem);
        });

        buttonDelete.setOnAction(actionEvent -> {
            var selectedItem = generalItemsTableView.getTableView().getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            generalItemsTableView.getSourceCollection().remove(selectedItem);
        });

        buttonExit = new Button("Exit");
        buttonExit.setOnAction(actionEvent -> {
            stage.close();
        });
        buttonsHbox.getChildren().add(buttonExit);

        var vbox = new VBox();
        vbox.getChildren().add(generalItemsTableView.getTableView());
        vbox.getChildren().add(buttonsHbox);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);

        var scene = new Scene(vbox);
        stage.setTitle(title);
        stage.setScene(scene);
    }

    public void bindSourceCollection(ObservableList<TViewModel> sourceCollection) {
        generalItemsTableView.bindSourceCollection(sourceCollection);
    }

}
