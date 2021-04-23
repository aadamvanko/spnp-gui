package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.*;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

public class GraphComponent extends ApplicationComponent implements
        CursorModeChangeListener, CreateElementTypeChangeListener, ToggleGridSnappingListener,
        NewDiagramAddedListener, NewElementAddedListener, SelectedDiagramChangeListener {

    private TabPane tabPane;
    private Map<Tab, GraphView> graphViews;

    public GraphComponent(Model model, Notifications notifications) {
        super(model, notifications);

        createView();

        notifications.addCursorModeChangeListener(this);
        notifications.addCreateElementTypeChangeListener(this);
        notifications.addToggleGridSnappingListener(this);
        notifications.addNewDiagramAddedListener(this);
        notifications.addNewElementAddedListener(this);
        notifications.addSelectedDiagramChangeListener(this);
    }

    private void createView() {
        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        graphViews = new HashMap<>();

        tabPane.getSelectionModel().selectedItemProperty().addListener(changeEvent -> {
            var selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab == null) {
                model.selectDiagram(null);
                return;
            }

            var diagram = graphViews.get(selectedTab).getDiagramViewModel();
            model.selectDiagram(diagram);
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
    public void onCursorModeChanged(CursorMode cursorMode) {
        if (getSelectedGraphView() == null) {
            return;
        }
        System.out.println("Cursor mode changed to " + cursorMode);
        getSelectedGraphView().setCursorMode(cursorMode);
    }


    @Override
    public void onCreateElementTypeChanged(GraphElementType graphElementType) {
        if (getSelectedGraphView() == null) {
            return;
        }
        getSelectedGraphView().setCreateElementType(graphElementType);
    }

    @Override
    public void gridSnappingToggled() {
        if (getSelectedGraphView() == null) {
            return;
        }
        getSelectedGraphView().setSnappingToGrid(!getSelectedGraphView().isSnappingEnabled());
    }

    @Override
    public void onNewDiagramAdded(DiagramViewModel diagramViewModel) {
        var graphView = new GraphView(notifications);
        graphView.bindDiagramViewModel(diagramViewModel);
        var tabName = String.format("%s/%s", diagramViewModel.getProject().nameProperty().get(), diagramViewModel.nameProperty().get());
        addGraphView(tabName, graphView);
    }

    @Override
    public void onSelectedDiagramChanged(DiagramViewModel diagramViewModel) {
        if (diagramViewModel == null) {
            return;
        }

        if (isOpened(diagramViewModel)) {
            var tab = getTabForDiagram(diagramViewModel);
            tabPane.getSelectionModel().select(tab);
        } else {
            var tabName = String.format("%s/%s", diagramViewModel.getProject().nameProperty().get(), diagramViewModel.nameProperty().get());
            var graphView = new GraphView(notifications);
            graphView.bindDiagramViewModel(diagramViewModel);
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
}
