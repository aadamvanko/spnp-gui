package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.BinomialTransitionDistribution;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.NegativeBinomialTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;

public class NegativeBinomialTransitionDistributionViewModel extends ThreeValuesTransitionDistributionBaseViewModel {

    public NegativeBinomialTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link NegativeBinomialTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param numberValue      number value of negative binomial distribution
     * @param probabilityValue probability value of negative binomial distribution
     * @param tValue           T value of negative binomial distribution
     */
    public NegativeBinomialTransitionDistributionViewModel(String numberValue,
                                                           String probabilityValue,
                                                           String tValue) {
        super(numberValue, probabilityValue, tValue);
    }

    /**
     * Creates new {@link NegativeBinomialTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param numberValueFunction      reference to a function which calculates number value of negative binomial distribution
     * @param probabilityValueFunction reference to a function which calculates probability value of negative binomial distribution
     * @param tValueFunction           reference to a function which calculates T value of negative binomial distribution
     */
    public NegativeBinomialTransitionDistributionViewModel(FunctionViewModel numberValueFunction,
                                                           FunctionViewModel probabilityValueFunction,
                                                           FunctionViewModel tValueFunction) {
        super(numberValueFunction, probabilityValueFunction, tValueFunction);
    }

    /**
     * Creates new {@link BinomialTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param numberValue      number value of negative binomial distribution
     * @param probabilityValue probability value of negative binomial distribution
     * @param tValue           T value of negative binomial distribution
     * @param dependentPlace   reference to a {@link StandardPlace} object which is used for distribution
     */
    public NegativeBinomialTransitionDistributionViewModel(String numberValue,
                                                           String probabilityValue,
                                                           String tValue,
                                                           PlaceViewModel dependentPlace) {
        super(numberValue, probabilityValue, tValue, dependentPlace);
    }

    public StringProperty numberValueProperty() {
        return firstValueProperty();
    }

    public StringProperty probabilityValueProperty() {
        return secondValueProperty();
    }

    public StringProperty TValueProperty() {
        return thirdValueProperty();
    }

    public FunctionViewModel getNumberValueFunction() {
        return this.getFirstFunction();
    }

    public void setNumberValueFunction(FunctionViewModel numberValueFunction) {
        this.setFirstFunction(numberValueFunction);
    }

    public FunctionViewModel getProbabilityValueFunction() {
        return this.getSecondFunction();
    }

    public void setProbabilityValueFunction(FunctionViewModel probabilityValueFunction) {
        this.setSecondFunction(probabilityValueFunction);
    }

    public FunctionViewModel getTValueFunction() {
        return this.getThirdFunction();
    }

    public void setTValueFunction(FunctionViewModel tValueFunction) {
        this.setThirdFunction(tValueFunction);
    }

}
