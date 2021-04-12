package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionController;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.*;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

public class GraphComponent extends ApplicationComponent implements
        CursorModeChangeListener, CreateElementTypeChangeListener, ToggleGridSnappingListener, NewDiagramAddedListener, SelectedDiagramChangeListener {

    private final TabPane tabPane;
    private final Map<Tab, GraphView> graphViews;
    private GraphView selectedGraphView;

    public GraphComponent(Model model, Notifications notifications) {
        super(model, notifications);

        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        graphViews = new HashMap<>();

        addGraphView("Diagram 1", createMockGraphView());
        addGraphView("Diagram 2", new GraphView(notifications));

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
    }

    private void addGraphView(String diagramName, GraphView graphView) {
        if (selectedGraphView == null) {
            selectedGraphView = graphView;
        }

        if (graphView.getDiagramViewModel() == null) {
            var diagramViewModel = new DiagramViewModel(model.getSelectedProject());
            graphView.setDiagramViewModel(diagramViewModel);
        }

        var tab = new Tab(diagramName, graphView.getZoomableScrollPane());
        graphViews.put(tab, graphView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private GraphView createMockGraphView() {
        PlaceController pc1 = new PlaceController(100, 100);
        PlaceController pc2 = new PlaceController(500, 100);
        PlaceController pc4 = new PlaceController(100, 200);
        TimedTransitionController ttc1 = new TimedTransitionController(300, 100);
        ArcController ac1 = new StandardArcController(pc1, ttc1);
        ArcController ac2 = new StandardArcController(ttc1, pc2);
        ImmediateTransitionController itc1 = new ImmediateTransitionController(300, 200);
        ArcController ac3 = new InhibitorArcController(pc4, itc1);
        PlaceController pc3 = new PlaceController(100, 500);

        GraphView mockGraphView = new GraphView(notifications);
        pc1.addToParent(mockGraphView);
        pc2.addToParent(mockGraphView);
        ttc1.addToParent(mockGraphView);
        ac1.addToParent(mockGraphView);
        ac2.addToParent(mockGraphView);
        itc1.addToParent(mockGraphView);
        pc3.addToParent(mockGraphView);
        pc4.addToParent(mockGraphView);
        ac3.addToParent(mockGraphView);
        return mockGraphView;
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
        graphView.setDiagramViewModel(diagramViewModel);
        var tabName = String.format("%s/%s", diagramViewModel.getProject().nameProperty().get(), diagramViewModel.nameProperty().get());
        addGraphView(tabName, graphView);
    }

    @Override
    public void onSelectedDiagramChanged(DiagramViewModel diagramViewModel) {
    }
}
