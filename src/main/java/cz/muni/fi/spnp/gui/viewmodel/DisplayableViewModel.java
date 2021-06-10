package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class DisplayableViewModel {
    private final StringProperty name = new SimpleStringProperty("nameUnset");

    public DisplayableViewModel() {
    }

    public DisplayableViewModel(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
