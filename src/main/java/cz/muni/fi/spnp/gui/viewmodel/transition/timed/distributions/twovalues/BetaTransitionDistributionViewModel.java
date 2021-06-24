package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;

public class BetaTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel<Double, Double> {
    public BetaTransitionDistributionViewModel(Double firstValue, Double secondValue) {
        super(firstValue, secondValue);
    }

    public BetaTransitionDistributionViewModel(FunctionViewModel firstValueFunction, FunctionViewModel secondValueFunction) {
        super(firstValueFunction, secondValueFunction);
    }

    public BetaTransitionDistributionViewModel(Double firstValue, Double secondValue, PlaceViewModel dependentPlace) {
        super(firstValue, secondValue, dependentPlace);
    }
}
