package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.TransitionProbabilityViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

/**
 * View for the immediate transition.
 */
public class ImmediateTransitionView extends TransitionView {

    private final static double WIDTH = 9;
    private final static double HEIGHT = 30;

    private final ChangeListener<TransitionProbabilityViewModel> onProbabilityChangedListener = this::onProbabilityChangedListener;

    public ImmediateTransitionView(GraphView graphView, ImmediateTransitionViewModel immediateTransitionViewModel) {
        super(graphView, immediateTransitionViewModel);

        createView();
        bindViewModel();
    }

    private void onProbabilityChangedListener(ObservableValue<? extends TransitionProbabilityViewModel> observableValue, TransitionProbabilityViewModel oldValue, TransitionProbabilityViewModel newValue) {
        if (oldValue != null) {
            probabilityTypeLabel.textProperty().unbind();
        }
        if (newValue != null) {
            probabilityTypeLabel.textProperty().bind(newValue.representationProperty());
        }
    }

    @Override
    public ImmediateTransitionViewModel getViewModel() {
        return (ImmediateTransitionViewModel) super.getViewModel();
    }

    @Override
    protected double getRectangleDefaultWidth() {
        return WIDTH;
    }

    @Override
    protected double getRectangleDefaultHeight() {
        return HEIGHT;
    }

    private void createView() {
        rectangle.setFill(Color.BLACK);
        rectangle.setSmooth(true);
    }

    @Override
    protected void bindViewModel() {
        super.bindViewModel();

        probabilityTypeLabel.textProperty().bind(getViewModel().getTransitionProbability().representationProperty());
        getViewModel().transitionProbabilityProperty().addListener(this.onProbabilityChangedListener);
    }

    @Override
    public void unbindViewModel() {
        probabilityTypeLabel.textProperty().unbind();
        getViewModel().transitionProbabilityProperty().removeListener(this.onProbabilityChangedListener);

        super.unbindViewModel();
    }
}
