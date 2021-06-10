package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PlaceDependentTransitionProbabilityViewModel implements TransitionProbabilityViewModel {

    private final DoubleProperty value = new SimpleDoubleProperty(0.0);
    private PlaceViewModel dependentPlace;

    public DoubleProperty valueProperty() {
        return value;
    }

    public PlaceViewModel getDependentPlace() {
        return dependentPlace;
    }

    public void setDependentPlace(PlaceViewModel dependentPlace) {
        this.dependentPlace = dependentPlace;
    }
}
