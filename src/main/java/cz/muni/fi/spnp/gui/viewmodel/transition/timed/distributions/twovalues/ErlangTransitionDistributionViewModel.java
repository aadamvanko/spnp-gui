package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

public class ErlangTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel<Double, Integer> {

    public ErlangTransitionDistributionViewModel(Double rate, Integer numberOfPhases) {
        super(rate, numberOfPhases);
    }

    public ErlangTransitionDistributionViewModel(FunctionViewModel rateFunction, FunctionViewModel numberOfPhasesFunction) {
        super(rateFunction, numberOfPhasesFunction);
    }

    public ErlangTransitionDistributionViewModel(Double rate, Integer numberOfPhases, PlaceViewModel dependentPlace) {
        super(rate, numberOfPhases, dependentPlace);
    }

    public DoubleProperty rateProperty() {
        return DoubleProperty.doubleProperty(firstValueProperty());
    }

    public IntegerProperty numberOfPhasesProperty() {
        return IntegerProperty.integerProperty(secondValueProperty());
    }

    public FunctionViewModel rateFunctionProperty() {
        return getFirstFunction();
    }

    public FunctionViewModel numberOfPhasesFunctionProperty() {
        return getSecondFunction();
    }
}
