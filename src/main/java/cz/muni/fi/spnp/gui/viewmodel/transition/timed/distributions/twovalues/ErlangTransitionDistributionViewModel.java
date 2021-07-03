package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class ErlangTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public ErlangTransitionDistributionViewModel(String rate, String numberOfPhases) {
        super(rate, numberOfPhases);
    }

    public ErlangTransitionDistributionViewModel(FunctionViewModel rateFunction, FunctionViewModel numberOfPhasesFunction) {
        super(rateFunction, numberOfPhasesFunction);
    }

    public ErlangTransitionDistributionViewModel(String rate, String numberOfPhases, PlaceViewModel dependentPlace) {
        super(rate, numberOfPhases, dependentPlace);
    }

    public StringProperty rateProperty() {
        return firstValueProperty();
    }

    public StringProperty numberOfPhasesProperty() {
        return secondValueProperty();
    }

    public FunctionViewModel rateFunctionProperty() {
        return getFirstFunction();
    }

    public FunctionViewModel numberOfPhasesFunctionProperty() {
        return getSecondFunction();
    }
}
