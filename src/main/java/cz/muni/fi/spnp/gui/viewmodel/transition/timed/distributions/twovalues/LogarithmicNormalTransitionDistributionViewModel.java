package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.LogarithmicNormalTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;

public class LogarithmicNormalTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public LogarithmicNormalTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link LogarithmicNormalTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param firstValue  first value of logarithmic normal distribution
     * @param secondValue second value of logarithmic normal distribution
     */
    public LogarithmicNormalTransitionDistributionViewModel(String firstValue, String secondValue) {
        super(firstValue, secondValue);
    }

    /**
     * Creates new {@link LogarithmicNormalTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param firstFunction  reference to a function which calculates first value of logarithmic normal distribution
     * @param secondFunction reference to a function which calculates second value of logarithmic normal distribution
     */
    public LogarithmicNormalTransitionDistributionViewModel(FunctionViewModel firstFunction, FunctionViewModel secondFunction) {
        super(firstFunction, secondFunction);
    }

    /**
     * Creates new {@link LogarithmicNormalTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param firstValue     first value of logarithmic normal distribution
     * @param secondValue    second value of logarithmic normal distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public LogarithmicNormalTransitionDistributionViewModel(String firstValue, String secondValue, PlaceViewModel dependentPlace) {
        super(firstValue, secondValue, dependentPlace);
    }
}
