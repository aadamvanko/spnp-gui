package cz.muni.fi.spnp.gui.components;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;

public abstract class ApplicationComponent implements UIComponent {

    protected Model model;
    protected Notifications notifications;

    public ApplicationComponent(Model model, Notifications notifications) {
        this.model = model;
        this.notifications = notifications;
    }
}
