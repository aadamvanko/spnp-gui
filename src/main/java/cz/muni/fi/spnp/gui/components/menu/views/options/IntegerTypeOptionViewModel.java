package cz.muni.fi.spnp.gui.components.menu.views.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.Option;
import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

public class IntegerTypeOptionViewModel extends OptionViewModel {

    private final int value;

    public IntegerTypeOptionViewModel(OptionKey key, int defaultValue) {
        super(key);

        if (!key.toString().startsWith("IOP"))
            throw new IllegalArgumentException("Invalid Option key for integer type Option.");

        this.value = defaultValue;
    }

    public int getValue() {
        return value;
    }

}
