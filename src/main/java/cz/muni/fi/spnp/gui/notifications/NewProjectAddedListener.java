package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

public interface NewProjectAddedListener {
    void onNewProjectAdded(ProjectViewModel projectViewModel);
}
