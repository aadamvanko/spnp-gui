package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionController;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.scene.Node;

public class GraphComponent extends ApplicationComponent {

    private final GraphView graphView;

    public GraphComponent(Model model) {
        super(model);

        PlaceController pc1 = new PlaceController(100, 100);
        PlaceController pc2 = new PlaceController(500, 100);
        PlaceController pc4 = new PlaceController(100, 200);
        TimedTransitionController ttc1 = new TimedTransitionController(300, 100);
        ArcController ac1 = new StandardArcController(pc1, ttc1);
        ArcController ac2 = new StandardArcController(ttc1, pc2);
        ImmediateTransitionController itc1 = new ImmediateTransitionController(300, 200);
        ArcController ac3 = new InhibitorArcController(pc4, itc1);
        PlaceController pc3 = new PlaceController(100, 500);

        graphView = new GraphView();
        pc1.addToParent(graphView);
        pc2.addToParent(graphView);
        ttc1.addToParent(graphView);
        ac1.addToParent(graphView);
        ac2.addToParent(graphView);
        itc1.addToParent(graphView);
        pc3.addToParent(graphView);
        pc4.addToParent(graphView);
        ac3.addToParent(graphView);
    }

    @Override
    public Node getRoot() {
        return graphView.getZoomableScrollPane();
    }
}
