package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.TruncatedNormalTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;

public class TruncatedNormalTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel<Double, Double> {

    /**
     * Creates new {@link TruncatedNormalTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param expectation expectation value of truncated normal distribution
     * @param variance    variance value of truncated normal distribution
     */
    public TruncatedNormalTransitionDistributionViewModel(Double expectation, Double variance) {
        super(expectation, variance);
    }

    /**
     * Creates new {@link TruncatedNormalTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param expectationFunction reference to a function which calculates expectation value of truncated normal distribution
     * @param varianceFunction    reference to a function which calculates variance value of truncated normal distribution
     */
    public TruncatedNormalTransitionDistributionViewModel(FunctionViewModel expectationFunction, FunctionViewModel varianceFunction) {
        super(expectationFunction, varianceFunction);
    }

    /**
     * Creates new {@link TruncatedNormalTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param expectation    expectation value of truncated normal distribution
     * @param variance       variance value of truncated normal distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public TruncatedNormalTransitionDistributionViewModel(Double expectation, Double variance, PlaceViewModel dependentPlace) {
        super(expectation, variance, dependentPlace);
    }

    public DoubleProperty expectationProperty() {
        return DoubleProperty.doubleProperty(firstValueProperty());
    }

    public DoubleProperty varianceProperty() {
        return DoubleProperty.doubleProperty(secondValueProperty());
    }

    public FunctionViewModel getExpectationFunction() {
        return this.getFirstFunction();
    }

    public void setExpectationFunction(FunctionViewModel expectationFunction) {
        this.setFirstFunction(expectationFunction);
    }

    public FunctionViewModel getVarianceFunction() {
        return this.getSecondFunction();
    }

    public void setVarianceFunction(FunctionViewModel varianceFunction) {
        this.setSecondFunction(varianceFunction);
    }

}
