package cz.muni.fi.spnp.gui.viewmodel.transition;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TimedTransitionViewModel extends TransitionViewModel {

    private final ObjectProperty<TransitionDistributionType> transitionDistributionType = new SimpleObjectProperty<>();
    private final ObjectProperty<DistributionType> distributionType = new SimpleObjectProperty<>();


//
//    public TimedTransitionViewModel(String name, double x, double y, int priority, TransitionDistributionType transitionDistributionType, DistributionType distributionType) {
//        super(name, x, y, priority);
//        this.transitionDistributionType.set(transitionDistributionType);
//        this.distributionType.set(distributionType);
//    }


    public ObjectProperty<TransitionDistributionType> transitionDistributionTypeProperty() {
        return transitionDistributionType;
    }

    public ObjectProperty<DistributionType> distributionTypeProperty() {
        return distributionType;
    }
}
