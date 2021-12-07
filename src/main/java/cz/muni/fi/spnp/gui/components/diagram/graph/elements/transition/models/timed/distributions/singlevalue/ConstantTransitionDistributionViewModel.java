package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.singlevalue;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

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
