package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.*;

public class ImmediateTransitionViewModel extends TransitionViewModel {

    private final ObjectProperty<TransitionProbabilityType> transitionProbabilityType = new SimpleObjectProperty<>();
    private final DoubleProperty constantProbability = new SimpleDoubleProperty();
    private final StringProperty functionalProbability = new SimpleStringProperty();
    private final StringProperty placeDependantProbability = new SimpleStringProperty();

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
