package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    private final List<CursorModeChangeListener> cursorModeChangeListeners;
    private final List<CreateElementTypeChangeListener> createElementTypeChangeListeners;
    private final List<ToggleGridSnappingListener> toggleGridSnappingListeners;
    private final List<SelectedElementsChangeListener> selectedElementsChangeListeners;
    private final List<NewElementAddedListener> newElementAddedListeners;
    private final List<ElementRemovedListener> elementRemovedListeners;

    public Notifications() {
        cursorModeChangeListeners = new ArrayList<>();
        createElementTypeChangeListeners = new ArrayList<>();
        toggleGridSnappingListeners = new ArrayList<>();
        selectedElementsChangeListeners = new ArrayList<>();
        newElementAddedListeners = new ArrayList<>();
        elementRemovedListeners = new ArrayList<>();
    }

    public void addCursorModeChangeListener(CursorModeChangeListener listener) {
        cursorModeChangeListeners.add(listener);
    }

    public void removeCursorModeChangeListener(CursorModeChangeListener listener) {
        cursorModeChangeListeners.remove(listener);
    }

    public void setGraphCursorMode(CursorMode cursorMode) {
        cursorModeChangeListeners.forEach(listener -> listener.onCursorModeChanged(cursorMode));
    }

    public void addCreateElementTypeChangeListener(CreateElementTypeChangeListener listener) {
        createElementTypeChangeListeners.add(listener);
    }

    public void removeCreateElementTypeListener(CreateElementTypeChangeListener listener) {
        createElementTypeChangeListeners.remove(listener);
    }

    public void setCreateElementType(GraphElementType graphElementType) {
        createElementTypeChangeListeners.forEach(listener -> listener.onCreateElementTypeChanged(graphElementType));
    }

    public void addToggleGridSnappingListener(ToggleGridSnappingListener listener) {
        toggleGridSnappingListeners.add(listener);
    }

    public void removeToggleGridSnappingListener(ToggleGridSnappingListener listener) {
        toggleGridSnappingListeners.remove(listener);
    }

    public void toggleGridSnapping() {
        toggleGridSnappingListeners.forEach(listener -> listener.gridSnappingToggled());
    }

    public void addSelectedElementsChangeListener(SelectedElementsChangeListener listener) {
        selectedElementsChangeListeners.add(listener);
    }

    public void removeSelectedElementsChangeListener(SelectedElementsChangeListener listener) {
        selectedElementsChangeListeners.remove(listener);
    }

    public void selectedElementsChanged(List<GraphElement> selectedElements) {
        selectedElementsChangeListeners.forEach(listener -> listener.onSelectedElementsChanged(selectedElements));
    }

    public void addNewElementAddedListener(NewElementAddedListener listener) {
        newElementAddedListeners.add(listener);
    }

    public void removeNewElementAddedListener(NewElementAddedListener listener) {
        newElementAddedListeners.remove(listener);
    }

    public void newElementAdded(ElementViewModel elementViewModel) {
        newElementAddedListeners.forEach(listener -> listener.onNewElementAdded(elementViewModel));
    }

    public void addElementRemovedListener(ElementRemovedListener listener) {
        elementRemovedListeners.add(listener);
    }

    public void removeElementRemovedListener(ElementRemovedListener listener) {
        elementRemovedListeners.remove(listener);
    }

    public void elementRemoved(ElementViewModel elementViewModel) {
        elementRemovedListeners.forEach(listener -> listener.onElementRemoved(elementViewModel));
    }
}
