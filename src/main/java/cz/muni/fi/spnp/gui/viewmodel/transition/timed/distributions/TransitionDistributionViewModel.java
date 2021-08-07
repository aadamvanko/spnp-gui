package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public interface TransitionDistributionViewModel {

    ObjectProperty<TransitionDistributionType> distributionTypeProperty();

    ObjectProperty<PlaceViewModel> dependentPlaceProperty();

    void setDependentPlace(PlaceViewModel dependentPlace);

    TimedDistributionType getEnumType();

    List<StringProperty> getValues();

    List<String> getValuesNames();

    List<ObjectProperty<FunctionViewModel>> getFunctions();
}
