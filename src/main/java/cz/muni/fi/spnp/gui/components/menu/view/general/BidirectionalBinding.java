package cz.muni.fi.spnp.gui.components.menu.view.general;

import javafx.beans.property.Property;

/**
 * Helper class for representing bidirectional binding relationship.
 *
 * @param <T> JavaFX property class type
 */
public class BidirectionalBinding<T extends Property> {
    public T first;
    public T second;

    public BidirectionalBinding(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public void unbind() {
        first.unbindBidirectional(second);
    }
}
