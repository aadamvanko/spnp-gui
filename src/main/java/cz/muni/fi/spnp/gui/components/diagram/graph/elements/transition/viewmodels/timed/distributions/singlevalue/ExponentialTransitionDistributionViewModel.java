package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.singlevalue;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Exponential transition distribution view model for the timed transition.
 */
public class ExponentialTransitionDistributionViewModel extends SingleValueTransitionDistributionBaseViewModel {

    public ExponentialTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link ExponentialTransitionDistributionViewModel} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param rate constant rate to be used in distribution
     */
    public ExponentialTransitionDistributionViewModel(String rate) {
        super(rate);
    }

    /**
     * Creates new {@link ExponentialTransitionDistributionViewModel} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param rateFunction reference to a function which calculates rate for distribution
     */
    public ExponentialTransitionDistributionViewModel(FunctionViewModel rateFunction) {
        super(rateFunction);
    }

    /**
     * Creates new {@link ExponentialTransitionDistributionViewModel} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param rate           constant to be used in exponential distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public ExponentialTransitionDistributionViewModel(String rate, PlaceViewModel dependentPlace) {
        super(rate, dependentPlace);
    }

    public StringProperty rateProperty() {
        return this.valueProperty();
    }

    public FunctionViewModel getRateFunction() {
        return this.getFirstFunction();
    }

    public void setRateFunction(FunctionViewModel rateFunction) {
        this.setFirstFunction(rateFunction);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Exponential;
    }

    @Override
    protected List<String> createValuesNames() {
        return List.of("Rate");
    }

}
