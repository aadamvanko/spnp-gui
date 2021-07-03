package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.WeibullTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;

public class WeibullTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    /**
     * Creates new {@link WeibullTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param alphaValue  alpha value of Weibull distribution
     * @param lambdaValue lambda value of Weibull distribution
     */
    public WeibullTransitionDistributionViewModel(String alphaValue, String lambdaValue) {
        super(alphaValue, lambdaValue);
    }

    /**
     * Creates new {@link WeibullTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param alphaValueFunction  reference to a function which calculates alpha value of Weibull distribution
     * @param lambdaValueFunction reference to a function which calculates lambda value of Weibull distribution
     */
    public WeibullTransitionDistributionViewModel(FunctionViewModel alphaValueFunction, FunctionViewModel lambdaValueFunction) {
        super(alphaValueFunction, lambdaValueFunction);
    }

    /**
     * Creates new {@link WeibullTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param alphaValue     alpha value of Weibull distribution
     * @param lambdaValue    lambda value of Weibull distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public WeibullTransitionDistributionViewModel(String alphaValue, String lambdaValue, PlaceViewModel dependentPlace) {
        super(alphaValue, lambdaValue, dependentPlace);
    }

    public StringProperty alphaValueProperty() {
        return firstValueProperty();
    }

    public StringProperty lambdaValueProperty() {
        return secondValueProperty();
    }

    public FunctionViewModel getAlphaValueFunction() {
        return this.getFirstFunction();
    }

    public void setAlphaValueFunction(FunctionViewModel alphaValueFunction) {
        this.setFirstFunction(alphaValueFunction);
    }

    public FunctionViewModel getLambdaValueFunction() {
        return this.getSecondFunction();
    }

    public void setLambdaValueFunction(FunctionViewModel lambdaValueFunction) {
        this.setSecondFunction(lambdaValueFunction);
    }

}
