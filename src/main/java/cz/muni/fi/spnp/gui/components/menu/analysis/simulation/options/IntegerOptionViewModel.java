package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

/**
 * View model for integer value option.
 */
public class IntegerOptionViewModel extends OptionViewModel<IntegerProperty> {

    public IntegerOptionViewModel(OptionKey optionKey, int value) {
        this(optionKey, value, false);
    }

    public IntegerOptionViewModel(OptionKey optionKey, int value, boolean use) {
        super(optionKey, new MySimpleIntegerProperty(value), use);
    }

    public int getValue() {
        return valueProperty().get();
    }

}
