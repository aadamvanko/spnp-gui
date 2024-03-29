package cz.muni.fi.spnp.gui.components.menu.view.general;

import cz.muni.fi.spnp.gui.components.common.ViewContainer;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import javafx.collections.ObservableList;

/**
 * Collapsable table view with the view model instances supporting operations add, edit and delete.
 */
public abstract class GeneralItemsTableViewCollapsable<TViewModel> extends ViewContainer {

    private final GeneralItemsTableView<TViewModel> generalItemsTableView;

    public GeneralItemsTableViewCollapsable(Model model, String title, GeneralItemsTableView<TViewModel> generalItemsTableView) {
        super(model, title);

        this.generalItemsTableView = generalItemsTableView;

        createView();
    }

    private void createView() {
        root.setContent(generalItemsTableView.getTableView());
    }

    public void bindSourceCollection(ObservableList<TViewModel> sourceCollection) {
        generalItemsTableView.bindSourceCollection(sourceCollection);
    }

}
