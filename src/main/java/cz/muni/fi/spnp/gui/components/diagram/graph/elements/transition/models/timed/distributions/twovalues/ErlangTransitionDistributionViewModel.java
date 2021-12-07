package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.twovalues;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Erlang transition distribution view model for the timed transition.
 */
public class ErlangTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public ErlangTransitionDistributionViewModel() {
    }

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

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Erlang;
    }

    @Override
    protected List<String> createValuesNames() {
        return List.of("Rate", "Number of phases");
    }

}
