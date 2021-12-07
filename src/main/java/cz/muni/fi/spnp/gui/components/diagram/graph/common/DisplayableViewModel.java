package cz.muni.fi.spnp.gui.components.diagram.graph.common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents items that can be shown in the side views, tables etc.
 */
public class DisplayableViewModel {
    private final StringProperty name = new SimpleStringProperty("nameUnset");

    public DisplayableViewModel() {
    }

    public DisplayableViewModel(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return "DisplayableViewModel{" +
                "name=" + getName() +
                '}';
    }
}
