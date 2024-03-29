package cz.muni.fi.spnp.gui.components.diagram.graph.elements;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcView;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.Connectable;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.MouseSelectable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * View for the connectable graph elements (places, transitions).
 */
public abstract class ConnectableGraphElementView extends GraphElementView implements Connectable, MouseSelectable {

    protected final static long UPDATE_DELAY = 50;

    private final List<ArcView> arcs;
    protected final Timer timer;

    public ConnectableGraphElementView(GraphView graphView, ConnectableViewModel connectableViewModel) {
        super(graphView, connectableViewModel);

        arcs = new ArrayList<>();
        timer = graphView.getModel().getTimer();
    }

    protected void executeDelayedUpdate(Runnable runnable) {
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                },
                UPDATE_DELAY);
    }

    @Override
    public ConnectableViewModel getViewModel() {
        return (ConnectableViewModel) viewModel;
    }

    protected abstract Point2D getContainerPosition();

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
    }

    @Override
    public void onMouseReleasedHandler(MouseEvent mouseEvent) {
        super.onMouseReleasedHandler(mouseEvent);
        removeStraightLinesFromArcs();
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
