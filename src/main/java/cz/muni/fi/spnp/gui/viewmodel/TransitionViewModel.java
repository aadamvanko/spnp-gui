package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransitionViewModel extends ElementViewModel {

    private final IntegerProperty priority = new SimpleIntegerProperty();
    private final StringProperty guardFunction = new SimpleStringProperty();

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public StringProperty guardFunctionProperty() {
        return guardFunction;
    }
}
