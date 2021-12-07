package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.immediate;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionProbabilityType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Constant transition probability view model for the immediate transition.
 */
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
