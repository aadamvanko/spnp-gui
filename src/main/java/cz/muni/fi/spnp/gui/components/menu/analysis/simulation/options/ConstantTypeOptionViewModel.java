package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ConstantTypeOptionViewModel extends OptionViewModel {

    private final ConstantValue value;
    private final ObservableList<ConstantValue> allowedValues;

    public ConstantTypeOptionViewModel(OptionKey key, ConstantValue defaultValue, List<ConstantValue> allowedValues) {
        super(key);

        if (defaultValue == null)
            throw new IllegalArgumentException("Value is not defined.");

        this.value = defaultValue;
        this.allowedValues = FXCollections.observableArrayList(allowedValues);
    }

    public ConstantValue getValue() {
        return value;
    }
}
