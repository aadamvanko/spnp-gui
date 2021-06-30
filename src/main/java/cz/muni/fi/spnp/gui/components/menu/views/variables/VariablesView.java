package cz.muni.fi.spnp.gui.components.menu.views.variables;

import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.general.GeneralTableView;
import cz.muni.fi.spnp.gui.components.menu.views.general.ItemViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.VariableViewModel;

public class VariablesView extends GeneralTableView<VariableViewModel> {

    public VariablesView() {
        super("Variables");

        addColumn("Name", "name");
        addColumn("Kind", "kind");
        addColumn("Type", "type");
        addColumn("Value", "value");

        buttonAdd.setOnMouseClicked(mouseEvent -> {
            var itemView = new VariableView(new VariableViewModel(), ItemViewMode.ADD);
            itemView.bindDiagramViewModel(this.diagramViewModel);
            itemView.getStage().setTitle("Add variable");
            itemView.getStage().showAndWait();
        });

        buttonEdit.setOnMouseClicked(mouseEvent -> {
            var selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            var itemView = new VariableView(selectedItem, ItemViewMode.EDIT);
            itemView.bindDiagramViewModel(this.diagramViewModel);
            itemView.getStage().setTitle("Edit variable");
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
        this.tableView.setItems(diagramViewModel.getVariables());
    }
}
