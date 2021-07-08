package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ConstantTransitionProbabilityViewModel implements TransitionProbabilityViewModel {

    private final DoubleProperty value = new SimpleDoubleProperty(0.0);

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

}
