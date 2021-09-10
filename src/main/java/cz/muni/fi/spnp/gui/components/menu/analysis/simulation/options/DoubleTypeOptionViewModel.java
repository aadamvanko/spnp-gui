package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

public class DoubleTypeOptionViewModel extends OptionViewModel {

    private final double value;

    public DoubleTypeOptionViewModel(OptionKey key, double defaultValue) {
        super(key);

        if (!key.toString().startsWith("FOP"))
            throw new IllegalArgumentException("Invalid Option key for double type Option.");

        this.value = defaultValue;
    }

    public double getValue() {
        return value;
    }

}
