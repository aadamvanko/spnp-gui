package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Base class for all transition probability view models for the immediate transition.
 */
public abstract class TransitionProbabilityViewModelBase implements TransitionProbabilityViewModel {

    protected StringProperty representation;

    public TransitionProbabilityViewModelBase() {
        this.representation = new SimpleStringProperty();
    }

    protected abstract String generateRepresentation();

    protected void updateRepresentation() {
        representation.set(generateRepresentation());
    }

    @Override
    public StringProperty representationProperty() {
        return representation;
    }

}
