package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;

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
