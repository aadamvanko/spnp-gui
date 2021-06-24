package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionVisitor;
import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.HyperExponentialTransitionDistribution;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.ThreeValuesTransitionDistributionBase;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;

public class HyperExponentialTransitionDistributionViewModel extends ThreeValuesTransitionDistributionBaseViewModel<Double, Double, Double> {

    /**
     * Creates new {@link HyperExponentialTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param firstLambdaRate  first lambda rate value of hyper-exponential distribution
     * @param secondLambdaRate second lambda rate value of hyper-exponential distribution
     * @param probabilityValue probability value of hyper-exponential distribution
     */
    public HyperExponentialTransitionDistributionViewModel(Double firstLambdaRate,
                                                           Double secondLambdaRate,
                                                           Double probabilityValue) {
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
    public HyperExponentialTransitionDistributionViewModel(Double firstLambdaRate,
                                                           Double secondLambdaRate,
                                                           Double probabilityValue,
                                                           PlaceViewModel dependentPlace) {
        super(firstLambdaRate, secondLambdaRate, probabilityValue, dependentPlace);
    }

    // TODO this creates new DoubleProperty everytime, need to have always the same for detaching purposes!!!
    public DoubleProperty firstLambdaRateProperty() {
        return DoubleProperty.doubleProperty(firstValueProperty());
    }

    public DoubleProperty secondLambdaRateProperty() {
        return DoubleProperty.doubleProperty(secondValueProperty());
    }

    public DoubleProperty probabilityValueProperty() {
        return DoubleProperty.doubleProperty(thirdValueProperty());
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

}
