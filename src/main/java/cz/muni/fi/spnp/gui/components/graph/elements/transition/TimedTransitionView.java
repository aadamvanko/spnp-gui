package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class TimedTransitionView extends TransitionView {

    private final static double WIDTH = 15;
    private final static double HEIGHT = 30;

    private final ChangeListener<TransitionDistributionViewModel> onTransitionDistributionChangedListener;

    public TimedTransitionView(GraphView graphView, TimedTransitionViewModel timedTransitionViewModel) {
        super(graphView, timedTransitionViewModel);

        this.onTransitionDistributionChangedListener = this::onTransitionDistributionChangedListener;

        createView();
        bindViewModel();
    }

    private void onTransitionDistributionChangedListener(ObservableValue<? extends TransitionDistributionViewModel> observableValue, TransitionDistributionViewModel oldValue, TransitionDistributionViewModel newValue) {
        var typeToString = new HashMap<TransitionDistributionType, String>();
        typeToString.put(TransitionDistributionType.Constant, "C");
        typeToString.put(TransitionDistributionType.Functional, "F");
        typeToString.put(TransitionDistributionType.PlaceDependent, "#");
        var probabilityText = String.format("%s(...)", typeToString.get(newValue.distributionTypeProperty().get()));
        probabilityTypeLabel.setText(probabilityText);
    }

    @Override
    public TimedTransitionViewModel getViewModel() {
        return (TimedTransitionViewModel) super.getViewModel();
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
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        rectangle.setSmooth(true);
    }

    @Override
    protected void bindViewModel() {
        super.bindViewModel();

        getViewModel().transitionDistributionProperty().addListener(this.onTransitionDistributionChangedListener);
        onTransitionDistributionChangedListener(null, null, getViewModel().getTransitionDistribution());
    }

    @Override
    public void unbindViewModel() {
        getViewModel().transitionDistributionProperty().removeListener(this.onTransitionDistributionChangedListener);

        super.unbindViewModel();
    }

}
