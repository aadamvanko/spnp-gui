package cz.muni.fi.spnp.gui.components.graph.elements;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.Connectable;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class ConnectableGraphElementView extends GraphElementView implements Connectable, MouseSelectable {

    private final List<ArcView> arcs;

    public ConnectableGraphElementView(GraphView graphView, ConnectableViewModel connectableViewModel) {
        super(graphView, connectableViewModel);

        arcs = new ArrayList<>();
    }

    @Override
    public ConnectableViewModel getViewModel() {
        return (ConnectableViewModel) viewModel;
    }

    public void addArc(ArcView arcView) {
        arcs.add(arcView);
    }

    public void removeArc(ArcView arcView) {
        arcs.remove(arcView);
    }

    protected void updateArcs() {
        arcs.forEach(arc -> arc.updateEnds(this));
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

    protected void moveViaTranslate(Point2D offset) {
        var connectableViewModel = getViewModel();

        Point2D old = new Point2D(connectableViewModel.positionXProperty().get(), connectableViewModel.positionYProperty().get());
        Point2D newPos = preventNegativeCoordinates(old.add(offset));

        connectableViewModel.positionXProperty().set(newPos.getX());
        connectableViewModel.positionYProperty().set(newPos.getY());
    }
}
