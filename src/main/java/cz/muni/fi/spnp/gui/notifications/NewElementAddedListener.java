package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;

public interface NewElementAddedListener {
    void onNewElementAdded(ElementViewModel elementViewModel);
}
