package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.singlevalue;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

/**
 * Constant transition distribution view model for the timed transition.
 */
public class ConstantTransitionDistributionViewModel extends SingleValueTransitionDistributionBaseViewModel {

    public ConstantTransitionDistributionViewModel() {
    }

    public ConstantTransitionDistributionViewModel(String value) {
        super(value);
    }

    public ConstantTransitionDistributionViewModel(FunctionViewModel function) {
        super(function);
    }

    public ConstantTransitionDistributionViewModel(String value, PlaceViewModel dependentPlace) {
        super(value, dependentPlace);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Constant;
    }

}
