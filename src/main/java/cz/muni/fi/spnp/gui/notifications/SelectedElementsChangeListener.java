package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;

import java.util.List;

public interface SelectedElementsChangeListener {
    void onSelectedElementsChanged(List<GraphElementView> selectedElements);
}
