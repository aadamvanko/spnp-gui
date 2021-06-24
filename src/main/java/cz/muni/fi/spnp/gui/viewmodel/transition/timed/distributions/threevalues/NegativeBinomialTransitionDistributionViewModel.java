package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionVisitor;
import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.BinomialTransitionDistribution;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.NegativeBinomialTransitionDistribution;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.ThreeValuesTransitionDistributionBase;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;

public class NegativeBinomialTransitionDistributionViewModel extends ThreeValuesTransitionDistributionBaseViewModel<Double, Double, Double> {

    /**
     * Creates new {@link NegativeBinomialTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param numberValue      number value of negative binomial distribution
     * @param probabilityValue probability value of negative binomial distribution
     * @param tValue           T value of negative binomial distribution
     */
    public NegativeBinomialTransitionDistributionViewModel(Double numberValue,
                                                           Double probabilityValue,
                                                           Double tValue) {
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
    public NegativeBinomialTransitionDistributionViewModel(Double numberValue,
                                                           Double probabilityValue,
                                                           Double tValue,
                                                           PlaceViewModel dependentPlace) {
        super(numberValue, probabilityValue, tValue, dependentPlace);
    }

    public DoubleProperty numberValueProperty() {
        return DoubleProperty.doubleProperty(firstValueProperty());
    }

    public DoubleProperty probabilityValueProperty() {
        return DoubleProperty.doubleProperty(secondValueProperty());
    }

    public DoubleProperty TValueProperty() {
        return DoubleProperty.doubleProperty(thirdValueProperty());
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
