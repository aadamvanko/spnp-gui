package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

/**
 * Geometric transition distribution view model for the timed transition.
 */
public class GeometricTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public GeometricTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link GeometricTransitionDistributionViewModel} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param firstValue  first value of geometric distribution
     * @param secondValue second value of geometric distribution
     */
    public GeometricTransitionDistributionViewModel(String firstValue, String secondValue) {
        super(firstValue, secondValue);
    }

    /**
     * Creates new {@link GeometricTransitionDistributionViewModel} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param firstFunction  reference to a function which calculates first value of uniform distribution
     * @param secondFunction reference to a function which calculates second value of uniform distribution
     */
    public GeometricTransitionDistributionViewModel(FunctionViewModel firstFunction, FunctionViewModel secondFunction) {
        super(firstFunction, secondFunction);
    }

    /**
     * Creates new {@link GeometricTransitionDistributionViewModel} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param firstValue     first value of geometric distribution
     * @param secondValue    second value of geometric distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public GeometricTransitionDistributionViewModel(String firstValue, String secondValue, PlaceViewModel dependentPlace) {
        super(firstValue, secondValue, dependentPlace);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Geometric;
    }

}
