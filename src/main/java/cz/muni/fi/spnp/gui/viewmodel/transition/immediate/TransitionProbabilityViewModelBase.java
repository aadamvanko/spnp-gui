package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
