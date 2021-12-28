package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

/**
 * View model for integer value option.
 */
public class IntegerOptionViewModel extends OptionViewModel<IntegerProperty> {

    public IntegerOptionViewModel(int value) {
        this(value, false);
    }

    public IntegerOptionViewModel(int value, boolean use) {
        super(new MySimpleIntegerProperty(value), use);
    }

    public int getValue() {
        return valueProperty().get();
    }

}
