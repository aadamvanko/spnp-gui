package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
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
