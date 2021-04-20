package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TimedTransitionViewModel extends TransitionViewModel {

    private final ObjectProperty<TransitionDistributionType> transitionDistributionType = new SimpleObjectProperty<>();

    public TimedTransitionViewModel(String name, double x, double y, int priority, TransitionDistributionType transitionDistributionType) {
        super(name, x, y, priority);
        this.transitionDistributionTypeProperty().set(transitionDistributionType);
    }


    public ObjectProperty<TransitionDistributionType> transitionDistributionTypeProperty() {
        return transitionDistributionType;
    }
}
