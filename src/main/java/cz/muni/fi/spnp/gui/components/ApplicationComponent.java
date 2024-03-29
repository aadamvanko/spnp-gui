package cz.muni.fi.spnp.gui.components;

import cz.muni.fi.spnp.gui.components.common.UIComponent;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;

/**
 * Base class for all component classes.
 */
public abstract class ApplicationComponent implements UIComponent {

    protected Model model;

    public ApplicationComponent(Model model) {
        this.model = model;
    }
}
