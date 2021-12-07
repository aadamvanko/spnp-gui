package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.twovalues;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

/**
 * Beta transition distribution view model for the timed transition.
 */
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
