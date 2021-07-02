package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.*;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphComponent extends ApplicationComponent implements ToggleGridSnappingListener,
        NewElementAddedListener, ElementRemovedListener {

    private TabPane tabPane;
    private Map<Tab, GraphView> graphViews;
    private final ListChangeListener<? super DiagramViewModel> onDiagramsChangedListener;

    public GraphComponent(Model model, Notifications notifications) {
        super(model, notifications);

        createView();

        onDiagramsChangedListener = this::onDiagramsChangedListener;

        notifications.addToggleGridSnappingListener(this);
        notifications.addNewElementAddedListener(this);
        model.getProjects().addListener(this::onProjectsChangedListener);
        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
        notifications.addElementRemovedListener(this);
    }

    private void onProjectsChangedListener(ListChangeListener.Change<? extends ProjectViewModel> projectsChange) {
        while (projectsChange.next()) {
            if (projectsChange.wasAdded()) {
                projectsChange.getAddedSubList().forEach(added -> added.getDiagrams().addListener(onDiagramsChangedListener));
            } else if (projectsChange.wasRemoved()) {
                projectsChange.getAddedSubList().forEach(removed -> removed.getDiagrams().removeListener(onDiagramsChangedListener));
            }
        }
    }

    private void onDiagramsChangedListener(ListChangeListener.Change<? extends DiagramViewModel> diagramsChange) {
        while (diagramsChange.next()) {
            if (diagramsChange.wasAdded()) {
                for (var added : diagramsChange.getAddedSubList()) {
                    var graphView = new GraphView(notifications, model);
                    graphView.bindDiagramViewModel(added);
                    var tabName = createTabName(added);
                    addGraphView(tabName, graphView);
                }
            } else if (diagramsChange.wasRemoved()) {
                for (var removed : diagramsChange.getRemoved()) {
                    var tab = getTabForDiagram(removed);
                    if (tab != null) {
                        graphViews.remove(tab);
                    }
                }
            }
        }
    }

    private String createTabName(DiagramViewModel diagramViewModel) {
        return String.format("%s/%s", diagramViewModel.getProject().nameProperty().get(), diagramViewModel.nameProperty().get());
    }

    private void createView() {
        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        graphViews = new HashMap<>();

        tabPane.getSelectionModel().selectedItemProperty().addListener(changeEvent -> {
            var selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab == null) {
                model.selectedDiagramProperty().set(null);
                return;
            }

            var diagram = graphViews.get(selectedTab).getDiagramViewModel();
            model.selectedDiagramProperty().set(diagram);
        });
    }

    private void addGraphView(String tabName, GraphView graphView) {
        var tab = new Tab(tabName, graphView.getZoomableScrollPane());
        tab.setOnClosed(event -> {
            graphViews.remove(tab);
        });

        graphViews.put(tab, graphView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private GraphView getSelectedGraphView() {
        var selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            return null;
        }

        return graphViews.get(selectedTab);
    }

    @Override
    public Node getRoot() {
        return tabPane;
    }

    @Override
    public void gridSnappingToggled() {
        if (getSelectedGraphView() == null) {
            return;
        }
        getSelectedGraphView().setSnappingToGrid(!getSelectedGraphView().isSnappingEnabled());
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (newDiagram == null) {
            return;
        }

        if (isOpened(newDiagram)) {
            var tab = getTabForDiagram(newDiagram);
            tabPane.getSelectionModel().select(tab);
        } else {
            var tabName = createTabName(newDiagram);
            var graphView = new GraphView(notifications, model);
            graphView.bindDiagramViewModel(newDiagram);
            addGraphView(tabName, graphView);
        }
    }

    private Tab getTabForDiagram(DiagramViewModel diagramViewModel) {
        for (var entry : graphViews.entrySet()) {
            if (entry.getValue().getDiagramViewModel() == diagramViewModel) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean isOpened(DiagramViewModel diagramViewModel) {
        return getTabForDiagram(diagramViewModel) != null;
    }

    @Override
    public void onNewElementAdded(ElementViewModel elementViewModel) {
        if (getSelectedGraphView() == null) {
            return;
        }

        var graphElementFactory = new GraphElementFactory(getSelectedGraphView());
        graphElementFactory.createGraphElement(elementViewModel);
    }

    @Override
    public void onElementRemoved(ElementViewModel removedElement) {
        if (getSelectedGraphView() == null) {
            return;
        }

        var toRemove = getSelectedGraphView().getElements().stream()
                .filter(elementView -> elementView.getViewModel() == removedElement)
                .collect(Collectors.toList());
        toRemove.forEach(elementView -> elementView.removeFromParent(getSelectedGraphView()));
    }
}
