package cz.muni.fi.spnp.gui.components.graph.elements;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.Connectable;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class ConnectableGraphElementView extends GraphElementView implements Connectable, MouseSelectable {

    private final List<ArcView> arcs;
    private final ChangeListener<? super String> onNameChangedListener;

    public ConnectableGraphElementView(GraphView graphView, ConnectableViewModel connectableViewModel) {
        super(graphView, connectableViewModel);

        arcs = new ArrayList<>();
        this.onNameChangedListener = this::onNameChangedListener;
    }

    @Override
    public ConnectableViewModel getViewModel() {
        return (ConnectableViewModel) viewModel;
    }

    private void onNameChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        snapToPointLater();
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

        getViewModel().nameProperty().addListener(this.onNameChangedListener);
    }

    @Override
    public void unbindViewModel() {
        getViewModel().nameProperty().removeListener(this.onNameChangedListener);

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
