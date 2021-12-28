package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class OptionViewModel<TProperty> {

    private final TProperty value;
    private final BooleanProperty use;

    public OptionViewModel(TProperty value) {
        this(value, false);
    }

    public OptionViewModel(TProperty value, boolean use) {
        this.value = value;
        this.use = new SimpleBooleanProperty(use);
    }

    public TProperty valueProperty() {
        return value;
    }

    public BooleanProperty useProperty() {
        return use;
    }

}
