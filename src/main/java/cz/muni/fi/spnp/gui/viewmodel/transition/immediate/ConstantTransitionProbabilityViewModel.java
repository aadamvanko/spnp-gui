package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleDoubleProperty;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.DoubleProperty;

public class ConstantTransitionProbabilityViewModel extends TransitionProbabilityViewModelBase {

    private final DoubleProperty value = new MySimpleDoubleProperty(0.0);

    public ConstantTransitionProbabilityViewModel() {
        value.addListener((observable, oldValue, newValue) -> updateRepresentation());
        updateRepresentation();
    }

    @Override
    protected String generateRepresentation() {
        return String.valueOf(getValue());
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    @Override
    public TransitionProbabilityType getEnumType() {
        return TransitionProbabilityType.Constant;
    }

}
