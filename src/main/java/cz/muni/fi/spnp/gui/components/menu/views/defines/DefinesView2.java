package cz.muni.fi.spnp.gui.components.menu.views.defines;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralItemView;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.VariableDataType;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;
import javafx.beans.property.StringProperty;

public class DefinesView2 extends GeneralTableView<DefineViewModel> {

    public DefinesView2() {
        super("Defines");

        addColumn("Name", "name");
        addColumn("Expression", "expression");

        buttonAdd.setOnMouseClicked(mouseEvent -> {
            var itemView = new DefineView2(new DefineViewModel(), ItemViewMode.ADD);
            itemView.bindDiagramViewModel(this.diagramViewModel);
            itemView.getStage().setTitle("Add define");
            itemView.getStage().showAndWait();
        });

        buttonEdit.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            var itemView = new DefineView2(selectedItem, ItemViewMode.EDIT);
            itemView.bindDiagramViewModel(this.diagramViewModel);
            itemView.getStage().setTitle("Edit define");
            itemView.getStage().showAndWait();
        });

        buttonDelete.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            diagramViewModel.getDefines().remove(selectedItem);
        });
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        this.tableView.setItems(diagramViewModel.getDefines());
    }
}

