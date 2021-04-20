package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import javafx.beans.property.*;

public class ImmediateTransitionViewModel extends TransitionViewModel {

    private final ObjectProperty<TransitionProbabilityType> transitionProbabilityType = new SimpleObjectProperty<>();
    private final DoubleProperty constantProbability = new SimpleDoubleProperty();
    private final StringProperty functionalProbability = new SimpleStringProperty();
    private final StringProperty placeDependantProbability = new SimpleStringProperty();

    public ImmediateTransitionViewModel(String name, double x, double y, int priority) {
        super(name, x, y, priority);
    }

    public ObjectProperty<TransitionProbabilityType> transitionProbabilityTypeProperty() {
        return transitionProbabilityType;
    }

    public DoubleProperty constantProbabilityProperty() {
        return constantProbability;
    }

    public StringProperty functionalProbabilityProperty() {
        return functionalProbability;
    }

    public StringProperty placeDependantProbabilityProperty() {
        return placeDependantProbability;
    }
}
