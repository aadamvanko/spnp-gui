package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.HyperExponentialTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import javafx.beans.property.StringProperty;

import java.util.List;

public class HyperExponentialTransitionDistributionViewModel extends ThreeValuesTransitionDistributionBaseViewModel {

    public HyperExponentialTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link HyperExponentialTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param firstLambdaRate  first lambda rate value of hyper-exponential distribution
     * @param secondLambdaRate second lambda rate value of hyper-exponential distribution
     * @param probabilityValue probability value of hyper-exponential distribution
     */
    public HyperExponentialTransitionDistributionViewModel(String firstLambdaRate,
                                                           String secondLambdaRate,
                                                           String probabilityValue) {
        super(firstLambdaRate, secondLambdaRate, probabilityValue);
    }

    /**
     * Creates new {@link HyperExponentialTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param firstLambdaRateFunction  reference to a function which calculates first lambda rate value of hyper-exponential distribution
     * @param secondLambdaRateFunction reference to a function which calculates second lambda rate value of hyper-exponential distribution
     * @param probabilityValueFunction reference to a function which calculates probability value of hyper-exponential distribution
     */
    public HyperExponentialTransitionDistributionViewModel(FunctionViewModel firstLambdaRateFunction,
                                                           FunctionViewModel secondLambdaRateFunction,
                                                           FunctionViewModel probabilityValueFunction) {
        super(firstLambdaRateFunction, secondLambdaRateFunction, probabilityValueFunction);
    }

    /**
     * Creates new {@link HyperExponentialTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param firstLambdaRate  first lambda rate value of hyper-exponential distribution
     * @param secondLambdaRate second lambda rate value of hyper-exponential distribution
     * @param probabilityValue probability value of hyper-exponential distribution
     * @param dependentPlace   reference to a {@link StandardPlace} object which is used for distribution
     */
    public HyperExponentialTransitionDistributionViewModel(String firstLambdaRate,
                                                           String secondLambdaRate,
                                                           String probabilityValue,
                                                           PlaceViewModel dependentPlace) {
        super(firstLambdaRate, secondLambdaRate, probabilityValue, dependentPlace);
    }

    public StringProperty firstLambdaRateProperty() {
        return firstValueProperty();
    }

    public StringProperty secondLambdaRateProperty() {
        return secondValueProperty();
    }

    public StringProperty probabilityValueProperty() {
        return thirdValueProperty();
    }

    public FunctionViewModel getFirstLambdaRateFunction() {
        return this.getFirstFunction();
    }

    public void setFirstLambdaRateFunction(FunctionViewModel firstLambdaRateFunction) {
        this.setFirstFunction(firstLambdaRateFunction);
    }

    public FunctionViewModel getSecondLambdaRateFunction() {
        return this.getSecondFunction();
    }

    public void setSecondLambdaRateFunction(FunctionViewModel secondLambdaRateFunction) {
        this.setSecondFunction(secondLambdaRateFunction);
    }

    public FunctionViewModel getProbabilityValueFunction() {
        return this.getThirdFunction();
    }

    public void setProbabilityValueFunction(FunctionViewModel probabilityValueFunction) {
        this.setThirdFunction(probabilityValueFunction);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.HyperExponential;
    }

    @Override
    protected List<String> createValuesNames() {
        return List.of("Lambda rate 1", "Lambda rate 2", "Probability value");
    }

}
