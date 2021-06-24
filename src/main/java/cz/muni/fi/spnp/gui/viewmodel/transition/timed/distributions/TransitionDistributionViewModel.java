package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionFunctionsVisitor;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionVisitor;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.ObjectProperty;

public interface TransitionDistributionViewModel {

    ObjectProperty<TransitionDistributionType> distributionTypeProperty();

    PlaceViewModel getDependentPlace();

    void setDependentPlace(PlaceViewModel dependentPlace);
}
