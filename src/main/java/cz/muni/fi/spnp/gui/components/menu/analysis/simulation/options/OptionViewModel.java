package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class OptionViewModel<TProperty> {

    private final OptionKey optionKey;
    private final TProperty value;
    private final BooleanProperty use;

    public OptionViewModel(OptionKey optionKey, TProperty value) {
        this(optionKey, value, false);
    }

    public OptionViewModel(OptionKey optionKey, TProperty value, boolean use) {
        this.optionKey = optionKey;
        this.value = value;
        this.use = new SimpleBooleanProperty(use);
    }

    public OptionKey getOptionKey() {
        return optionKey;
    }

    public TProperty valueProperty() {
        return value;
    }

    public BooleanProperty useProperty() {
        return use;
    }

}
