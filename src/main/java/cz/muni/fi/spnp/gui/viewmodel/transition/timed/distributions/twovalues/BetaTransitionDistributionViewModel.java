package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;

public class BetaTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public BetaTransitionDistributionViewModel() {
    }

    public BetaTransitionDistributionViewModel(String firstValue, String secondValue) {
        super(firstValue, secondValue);
    }

    public BetaTransitionDistributionViewModel(FunctionViewModel firstValueFunction, FunctionViewModel secondValueFunction) {
        super(firstValueFunction, secondValueFunction);
    }

    public BetaTransitionDistributionViewModel(String firstValue, String secondValue, PlaceViewModel dependentPlace) {
        super(firstValue, secondValue, dependentPlace);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Beta;
    }

}
