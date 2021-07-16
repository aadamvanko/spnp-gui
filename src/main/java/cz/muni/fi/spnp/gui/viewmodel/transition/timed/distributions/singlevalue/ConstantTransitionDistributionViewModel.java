package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;

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
    public StringProperty valueProperty() {
        return super.valueProperty();
    }

    @Override
    public FunctionViewModel getFirstFunction() {
        return super.getFirstFunction();
    }
}
