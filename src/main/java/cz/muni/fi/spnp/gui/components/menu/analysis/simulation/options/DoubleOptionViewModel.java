package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

/**
 * View model for double value option.
 */
public class DoubleOptionViewModel extends OptionViewModel<DoubleProperty> {

    public DoubleOptionViewModel(OptionKey optionKey, double value) {
        this(optionKey, value, false);
    }

    public DoubleOptionViewModel(OptionKey optionKey, double value, boolean use) {
        super(optionKey, new MySimpleDoubleProperty(value), use);
    }

    public double getValue() {
        return valueProperty().get();
    }

}
