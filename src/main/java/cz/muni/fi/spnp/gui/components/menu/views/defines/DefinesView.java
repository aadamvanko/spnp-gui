package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemsTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;

public class DefinesView extends GeneralItemsTableView<DefineViewModel> {

    public DefinesView() {
        super("Defines", "define");

        addColumn("Name", "name");
        addColumn("Expression", "expression");

        buttonAdd.setOnMouseClicked(mouseEvent -> {
            var itemView = new DefineView(new DefineViewModel(), ItemViewMode.ADD);
            itemView.setSourceCollection(sourceCollection);
            itemView.getStage().setTitle("Add define");
            itemView.getStage().showAndWait();
        });

        buttonEdit.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            var itemView = new DefineView(selectedItem, ItemViewMode.EDIT);
            itemView.setSourceCollection(sourceCollection);
            itemView.getStage().setTitle("Edit define");
            itemView.getStage().showAndWait();
        });

        buttonDelete.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            sourceCollection.remove(selectedItem);
        });
    }

    @Override
    protected DefineViewModel createViewModel() {
        return new DefineViewModel();
    }

    @Override
    protected GeneralItemView<DefineViewModel> createItemViewAdd(DefineViewModel defineViewModel) {
        return new DefineView(defineViewModel, ItemViewMode.ADD);
    }

    @Override
    protected GeneralItemView<DefineViewModel> createItemViewEdit(DefineViewModel defineViewModel) {
        return new DefineView(defineViewModel, ItemViewMode.EDIT);
    }
}

