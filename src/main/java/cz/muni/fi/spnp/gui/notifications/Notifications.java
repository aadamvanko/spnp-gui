package cz.muni.fi.spnp.gui.notifications;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    private final List<CursorModeChangeListener> cursorModeChangeListeners;
    private final List<CreateElementTypeChangeListener> createElementTypeChangeListeners;
    private final List<ToggleGridSnappingListener> toggleGridSnappingListeners;
    private final List<SelectedElementsChangeListener> selectedElementsChangeListeners;
    private final List<SelectedDiagramChangeListener> selectedDiagramChangeListeners;

    public Notifications() {
        cursorModeChangeListeners = new ArrayList<>();
        createElementTypeChangeListeners = new ArrayList<>();
        toggleGridSnappingListeners = new ArrayList<>();
        selectedElementsChangeListeners = new ArrayList<>();
        selectedDiagramChangeListeners = new ArrayList<>();
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

    public void addSelectedDiagramChangeListener(SelectedDiagramChangeListener listener) {
        selectedDiagramChangeListeners.add(listener);
    }

    public void removeSelectedDiagramChangeListener(SelectedDiagramChangeListener listener) {
        selectedDiagramChangeListeners.remove(listener);
    }

    public void selectedDiagramChanged(DiagramViewModel diagramViewModel) {
        selectedDiagramChangeListeners.forEach(listener -> listener.onSelectedDiagramChanged(diagramViewModel));
    }
}
