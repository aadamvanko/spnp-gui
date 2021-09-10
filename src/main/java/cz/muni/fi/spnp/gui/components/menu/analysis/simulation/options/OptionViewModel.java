package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

public abstract class OptionViewModel {

    private final OptionKey key;

    protected OptionViewModel(OptionKey key) {
        this.key = key;
    }

    public OptionKey getKey() {
        return key;
    }

}
