package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 * Custom choice box to keep the selected item after the change of the items.
 *
 * @param <T> type of the items
 */
public class MyChoiceBox<T> extends ChoiceBox<T> {

    public void setItemsWithSelected(ObservableList<T> newItems) {
        var selectedItem = getSelectionModel().getSelectedItem();
        super.setItems(newItems);
        getSelectionModel().select(selectedItem);
    }

}
