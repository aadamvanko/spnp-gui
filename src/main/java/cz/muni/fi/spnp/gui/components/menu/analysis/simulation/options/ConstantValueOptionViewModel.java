package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * View model for constant value option.
 */
public class ConstantValueOptionViewModel extends OptionViewModel<ObjectProperty<ConstantValue>> {

    public ConstantValueOptionViewModel(ConstantValue constantValue) {
        this(constantValue, false);
    }

    public ConstantValueOptionViewModel(ConstantValue constantValue, boolean use) {
        super(new SimpleObjectProperty<>(constantValue), use);
    }

    public ConstantValue getValue() {
        return valueProperty().get();
    }

}
