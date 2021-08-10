package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleDoubleProperty;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PlaceDependentTransitionProbabilityViewModel implements TransitionProbabilityViewModel {

    private final DoubleProperty value = new MySimpleDoubleProperty(0.0);
    private final ObjectProperty<PlaceViewModel> dependentPlace = new SimpleObjectProperty<>();

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
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
