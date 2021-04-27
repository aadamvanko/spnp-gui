package cz.muni.fi.spnp.gui.components.graph.elements;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcController;
import cz.muni.fi.spnp.gui.components.graph.interfaces.Connectable;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class ConnectableGraphElement extends GraphElement implements Connectable, MouseSelectable {

    private final List<ArcController> arcs;

    public ConnectableGraphElement() {
        arcs = new ArrayList<>();
    }

    public void addArc(ArcController arcController) {
        arcs.add(arcController);
    }

    public void removeArc(ArcController arcController) {
        arcs.remove(arcController);
    }

    protected void updateArcs() {
        arcs.forEach(arc -> arc.updateEnds(this));
    }

    @Override
    public void removeFromParent(GraphView parent) {
        super.removeFromParent(parent);
    }

    @Override
    public void onMouseDraggedHandler(MouseEvent mouseEvent) {
        super.onMouseDraggedHandler(mouseEvent);
        updateArcs();
//        System.out.println("Connectable graph element dragged");
    }

    @Override
    public void onMouseReleasedHandler(MouseEvent mouseEvent) {
        super.onMouseReleasedHandler(mouseEvent);
        removeStraightLinesFromArcs();
        System.out.println("Connectable graph element released");
    }

    private void removeStraightLinesFromArcs() {
        arcs.forEach(arc -> arc.removeStraightConnections());
    }
}
