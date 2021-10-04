package cz.muni.fi.spnp.gui.components.graph.elements;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.Connectable;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public abstract class ConnectableGraphElementView extends GraphElementView implements Connectable, MouseSelectable {

    protected final static long UPDATE_DELAY = 50;

    private final List<ArcView> arcs;
    protected final Timer timer;

    public ConnectableGraphElementView(GraphView graphView, ConnectableViewModel connectableViewModel) {
        super(graphView, connectableViewModel);

        arcs = new ArrayList<>();
        timer = new Timer();
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

    protected void snapToPointLater() {
        if (getViewModel().getPreservedShapeCenter() == Point2D.ZERO) {
            return;
        }

        graphView.processLayoutChange();
        Platform.runLater(() -> {
            snapToPoint(getShapeCenter(), getContainerPosition(), getViewModel().getPreservedShapeCenter(), false);
            updateArcs();
        });
    }

    @Override
    public void snapToPreservedPosition() {
        if (getViewModel().getPreservedShapeCenter() == Point2D.ZERO) {
            return;
        }

        snapToPoint(getShapeCenter(), getContainerPosition(), getViewModel().getPreservedShapeCenter(), false);
        updateArcs();
    }

    @Override
    public void preservePosition() {
        getViewModel().setPreservedShapeCenter(getShapeCenter());
    }

    protected abstract Point2D getContainerPosition();

    @Override
    protected void bindViewModel() {
        super.bindViewModel();

        // TODO same for place and transition
    }

    @Override
    public void unbindViewModel() {
        // TODO same for place and transition

        super.unbindViewModel();
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
