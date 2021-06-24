package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.ObjectProperty;

public class ConstantTransitionDistributionViewModel extends SingleValueTransitionDistributionBaseViewModel<Double> {

    public ConstantTransitionDistributionViewModel(double value) {
        super(value);
    }

    public ConstantTransitionDistributionViewModel(FunctionViewModel function) {
        super(function);
    }

    public ConstantTransitionDistributionViewModel(double value, PlaceViewModel dependentPlace) {
        super(value, dependentPlace);
    }

    @Override
    public ObjectProperty<Double> valueProperty() {
        return super.valueProperty();
    }

    @Override
    public FunctionViewModel getFirstFunction() {
        return super.getFirstFunction();
    }
}
