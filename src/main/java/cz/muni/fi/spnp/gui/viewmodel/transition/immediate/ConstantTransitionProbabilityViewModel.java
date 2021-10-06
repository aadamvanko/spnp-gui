package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConstantTransitionProbabilityViewModel extends TransitionProbabilityViewModelBase {

    private final StringProperty value = new SimpleStringProperty("0.0");

    public ConstantTransitionProbabilityViewModel() {
        value.addListener((observable, oldValue, newValue) -> updateRepresentation());
        updateRepresentation();
    }

    @Override
    protected String generateRepresentation() {
        return getValue();
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    @Override
    public TransitionProbabilityType getEnumType() {
        return TransitionProbabilityType.Constant;
    }

}
