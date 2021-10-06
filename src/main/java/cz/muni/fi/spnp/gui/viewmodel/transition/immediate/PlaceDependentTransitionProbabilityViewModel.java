package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaceDependentTransitionProbabilityViewModel extends TransitionProbabilityViewModelBase {

    private final StringProperty value = new SimpleStringProperty("0.0");
    private final ObjectProperty<PlaceViewModel> dependentPlace = new SimpleObjectProperty<>();

    public PlaceDependentTransitionProbabilityViewModel() {
        value.addListener((observable, oldValue, newValue) -> updateRepresentation());
        dependentPlace.addListener((observable, oldValue, newValue) -> updateRepresentation());
        updateRepresentation();
    }

    @Override
    protected String generateRepresentation() {
        var placeName = getDependentPlace() == null ? "null" : getDependentPlace().getName();
        return String.format("#(%s)%s", placeName, getValue());
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public PlaceViewModel getDependentPlace() {
        return dependentPlace.get();
    }

    public ObjectProperty<PlaceViewModel> dependentPlaceProperty() {
        return dependentPlace;
    }

    @Override
    public TransitionProbabilityType getEnumType() {
        return TransitionProbabilityType.PlaceDependent;
    }

}
