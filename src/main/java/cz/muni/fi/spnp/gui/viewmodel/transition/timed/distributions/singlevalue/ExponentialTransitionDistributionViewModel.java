package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;

public class ExponentialTransitionDistributionViewModel extends SingleValueTransitionDistributionBaseViewModel<Double> {

    /**
     * Creates new {@link ExponentialTransitionDistributionViewModel} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param rate constant rate to be used in distribution
     */
    public ExponentialTransitionDistributionViewModel(double rate) {
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
    public ExponentialTransitionDistributionViewModel(double rate, PlaceViewModel dependentPlace) {
        super(rate, dependentPlace);
    }

    public DoubleProperty rateProperty() {
        return DoubleProperty.doubleProperty(this.valueProperty());
    }

    public FunctionViewModel getRateFunction() {
        return this.getFirstFunction();
    }

    public void setRateFunction(FunctionViewModel rateFunction) {
        this.setFirstFunction(rateFunction);
    }

}