package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransitionViewModel extends ConnectableElementViewModel {

    private final IntegerProperty priority = new SimpleIntegerProperty();
    private final StringProperty guardFunction = new SimpleStringProperty();

    public TransitionViewModel(String name, double x, double y, int priority) {
        super(name, x, y);
        this.priority.set(priority);
    }

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public StringProperty guardFunctionProperty() {
        return guardFunction;
    }
}
