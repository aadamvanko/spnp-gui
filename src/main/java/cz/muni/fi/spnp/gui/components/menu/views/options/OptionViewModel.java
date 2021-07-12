package cz.muni.fi.spnp.gui.components.menu.views.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.Option;
import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;
import javafx.beans.property.ObjectProperty;

import java.util.Objects;

public abstract class OptionViewModel {

    private final OptionKey key;

    protected OptionViewModel(OptionKey key) {
        this.key = key;
    }

    public OptionKey getKey() {
        return key;
    }

}
