package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

/**
 * View model for double value option.
 */
public class DoubleOptionViewModel extends OptionViewModel<DoubleProperty> {

    public DoubleOptionViewModel(double value) {
        this(value, false);
    }

    public DoubleOptionViewModel(double value, boolean use) {
        super(new MySimpleDoubleProperty(value), use);
    }

    public double getValue() {
        return valueProperty().get();
    }

}
