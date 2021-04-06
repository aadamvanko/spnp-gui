package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;

import java.util.List;

public interface SelectedElementsChangeListener {
    void onSelectedElementsChanged(List<GraphElement> selectedElements);
}
