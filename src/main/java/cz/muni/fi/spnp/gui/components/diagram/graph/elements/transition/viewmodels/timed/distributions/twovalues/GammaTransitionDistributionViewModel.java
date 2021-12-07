package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.GammaTransitionDistribution;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

/**
 * Gamma transition distribution view model for the timed transition.
 */
public class GammaTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public GammaTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link GammaTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param firstValue  first value of gamma distribution
     * @param secondValue second value of gamma distribution
     */
    public GammaTransitionDistributionViewModel(String firstValue, String secondValue) {
        super(firstValue, secondValue);
    }

    /**
     * Creates new {@link GammaTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param firstValueFunction  reference to a function which calculates first value of gamma distribution
     * @param secondValueFunction reference to a function which calculates second value of gamma distribution
     */
    public GammaTransitionDistributionViewModel(FunctionViewModel firstValueFunction, FunctionViewModel secondValueFunction) {
        super(firstValueFunction, secondValueFunction);
    }

    /**
     * Creates new {@link GammaTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param firstValue     first value of gamma distribution
     * @param secondValue    second value of gamma distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public GammaTransitionDistributionViewModel(String firstValue, String secondValue, PlaceViewModel dependentPlace) {
        super(firstValue, secondValue, dependentPlace);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Gamma;
    }

}
