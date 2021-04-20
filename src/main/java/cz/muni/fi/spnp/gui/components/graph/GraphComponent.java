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

    private final TabPane tabPane;
    private final Map<Tab, GraphView> graphViews;
    private GraphView selectedGraphView;

    public GraphComponent(Model model, Notifications notifications) {
        super(model, notifications);

        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        graphViews = new HashMap<>();

        tabPane.getSelectionModel().selectedItemProperty().addListener(changeEvent -> {
            var selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab == null) {
                return;
            }
            selectedGraphView = graphViews.get(selectedTab);
        });

        notifications.addCursorModeChangeListener(this);
        notifications.addCreateElementTypeChangeListener(this);
        notifications.addToggleGridSnappingListener(this);
        notifications.addNewDiagramAddedListener(this);
        notifications.addNewElementAddedListener(this);
        notifications.addSelectedDiagramChangeListener(this);
    }

    private void addGraphView(String diagramName, GraphView graphView) {
        if (selectedGraphView == null) {
            selectedGraphView = graphView;
        }

        if (graphView.getDiagramViewModel() == null) {
            var diagramViewModel = new DiagramViewModel(notifications, model.getSelectedProject());
            graphView.bindDiagramViewModel(diagramViewModel);
        }

        var tab = new Tab(diagramName, graphView.getZoomableScrollPane());
        graphViews.put(tab, graphView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    @Override
    public Node getRoot() {
        return tabPane;
    }

    @Override
    public void onCursorModeChanged(CursorMode cursorMode) {
        if (selectedGraphView == null) {
            return;
        }
        System.out.println("Cursor mode changed to " + cursorMode);
        selectedGraphView.setCursorMode(cursorMode);
    }


    @Override
    public void onCreateElementTypeChanged(GraphElementType graphElementType) {
        if (selectedGraphView == null) {
            return;
        }
        selectedGraphView.setCreateElementType(graphElementType);
    }

    @Override
    public void gridSnappingToggled() {
        if (selectedGraphView == null) {
            return;
        }
        selectedGraphView.setSnappingToGrid(!selectedGraphView.isSnappingEnabled());
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
    }

    @Override
    public void onNewElementAdded(ElementViewModel elementViewModel) {
        if (selectedGraphView == null) {
            return;
        }

        var graphElementFactory = new GraphElementFactory(selectedGraphView);
        graphElementFactory.createGraphElement(elementViewModel);
    }
}
