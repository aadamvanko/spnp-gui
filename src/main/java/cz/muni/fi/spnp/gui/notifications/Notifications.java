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
    private final List<NewProjectAddedListener> newProjectAddedListeners;
    private final List<NewDiagramAddedListener> newDiagramAddedListeners;
    private final List<NewElementAddedListener> newElementAddedListeners;
    private final List<DiagramRemovedListener> diagramRemovedListeners;
    private final List<ElementRemovedListener> elementRemovedListeners;

    public Notifications() {
        cursorModeChangeListeners = new ArrayList<>();
        createElementTypeChangeListeners = new ArrayList<>();
        toggleGridSnappingListeners = new ArrayList<>();
        selectedElementsChangeListeners = new ArrayList<>();
        newProjectAddedListeners = new ArrayList<>();
        newDiagramAddedListeners = new ArrayList<>();
        newElementAddedListeners = new ArrayList<>();
        diagramRemovedListeners = new ArrayList<>();
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

    public void addNewProjectAddedListener(NewProjectAddedListener listener) {
        newProjectAddedListeners.add(listener);
    }

    public void removeNewProjectAddedListener(NewProjectAddedListener listener) {
        newProjectAddedListeners.remove(listener);
    }

    public void newProjectAdded(ProjectViewModel projectViewModel) {
        newProjectAddedListeners.forEach(listener -> listener.onNewProjectAdded(projectViewModel));
    }

    public void addNewDiagramAddedListener(NewDiagramAddedListener listener) {
        newDiagramAddedListeners.add(listener);
    }

    public void removeNewDiagramAddedListener(NewDiagramAddedListener listener) {
        newDiagramAddedListeners.remove(listener);
    }

    public void newDiagramAdded(DiagramViewModel diagramViewModel) {
        newDiagramAddedListeners.forEach(listener -> listener.onNewDiagramAdded(diagramViewModel));
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

    public void addDiagramRemovedListener(DiagramRemovedListener listener) {
        diagramRemovedListeners.add(listener);
    }

    public void removeDiagramRemovedListener(DiagramRemovedListener listener) {
        diagramRemovedListeners.remove(listener);
    }

    public void diagramRemoved(DiagramViewModel diagramViewModel) {
        diagramRemovedListeners.forEach(listener -> listener.onDiagramRemoved(diagramViewModel));
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
