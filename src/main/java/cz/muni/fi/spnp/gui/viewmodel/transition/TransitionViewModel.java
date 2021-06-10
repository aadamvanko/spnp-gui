package cz.muni.fi.spnp.gui.viewmodel.transition;

import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransitionViewModel extends ConnectableViewModel {

    private final IntegerProperty priority = new SimpleIntegerProperty();
    private final StringProperty guardFunction = new SimpleStringProperty();

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public StringProperty guardFunctionProperty() {
        return guardFunction;
    }
}
