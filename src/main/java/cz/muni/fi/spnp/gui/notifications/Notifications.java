package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    private final List<SelectedElementsChangeListener> selectedElementsChangeListeners;

    public Notifications() {
        selectedElementsChangeListeners = new ArrayList<>();
    }

    public void addSelectedElementsChangeListener(SelectedElementsChangeListener listener) {
        selectedElementsChangeListeners.add(listener);
    }

    public void removeSelectedElementsChangeListener(SelectedElementsChangeListener listener) {
        selectedElementsChangeListeners.remove(listener);
    }

    public void selectedElementsChanged(List<GraphElementView> selectedElements) {
        selectedElementsChangeListeners.forEach(listener -> listener.onSelectedElementsChanged(selectedElements));
    }

}
