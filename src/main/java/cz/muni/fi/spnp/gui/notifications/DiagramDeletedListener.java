package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public interface DiagramDeletedListener {
    void onDiagramDeleted(DiagramViewModel deletedDiagram);
}
