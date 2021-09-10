package cz.muni.fi.spnp.gui.components.menu.view.general;

import cz.muni.fi.spnp.gui.components.ViewContainer;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.collections.ObservableList;

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
