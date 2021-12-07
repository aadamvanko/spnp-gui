package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Allows different transition distribution types.
 */
public interface TransitionDistributionViewModel {

    /**
     * @return distribution type property
     */
    ObjectProperty<TransitionDistributionType> distributionTypeProperty();

    /**
     * @return property with the dependant place
     */
    ObjectProperty<PlaceViewModel> dependentPlaceProperty();

    /**
     * @param dependentPlace sets dependant place
     */
    void setDependentPlace(PlaceViewModel dependentPlace);

    /**
     * @return transition distribution type
     */
    TimedDistributionType getEnumType();

    /**
     * @return distribution parameters
     */
    List<StringProperty> getValues();

    /**
     * @return names of the distribution parameters
     */
    List<String> getValuesNames();

    /**
     * @return properties referencing the distribution functions
     */
    List<ObjectProperty<FunctionViewModel>> getFunctions();

}
