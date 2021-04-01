package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    private final List<CursorModeChangeListener> cursorModeChangeListeners;
    private final List<CreateElementTypeChangeListener> createElementTypeChangeListeners;
    private final List<ToggleGridSnappingListener> toggleGridSnappingListeners;

    public Notifications() {
        cursorModeChangeListeners = new ArrayList<>();
        createElementTypeChangeListeners = new ArrayList<>();
        toggleGridSnappingListeners = new ArrayList<>();
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

}
