package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.threevalues;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Binomial transition distribution view model for the timed transition.
 */
public class BinomialTransitionDistributionViewModel extends ThreeValuesTransitionDistributionBaseViewModel {

    public BinomialTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link BinomialTransitionDistributionViewModel} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param numberValue      number value of binomial distribution
     * @param probabilityValue probability value of binomial distribution
     * @param tValue           T value of binomial distribution
     */
    public BinomialTransitionDistributionViewModel(String numberValue,
                                                   String probabilityValue,
                                                   String tValue) {
        super(numberValue, probabilityValue, tValue);
    }

    /**
     * Creates new {@link BinomialTransitionDistributionViewModel} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param numberValueFunction      reference to a function which calculates number value of binomial distribution
     * @param probabilityValueFunction reference to a function which calculates probability value of binomial distribution
     * @param tValueFunction           reference to a function which calculates T value of binomial distribution
     */
    public BinomialTransitionDistributionViewModel(FunctionViewModel numberValueFunction,
                                                   FunctionViewModel probabilityValueFunction,
                                                   FunctionViewModel tValueFunction) {
        super(numberValueFunction, probabilityValueFunction, tValueFunction);
    }

    /**
     * Creates new {@link BinomialTransitionDistributionViewModel} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param numberValue      number value of binomial distribution
     * @param probabilityValue probability value of binomial distribution
     * @param tValue           T value of binomial distribution
     * @param dependentPlace   reference to a {@link PlaceViewModel} object which is used for distribution
     */
    public BinomialTransitionDistributionViewModel(String numberValue,
                                                   String probabilityValue,
                                                   String tValue,
                                                   PlaceViewModel dependentPlace) {
        super(numberValue, probabilityValue, tValue, dependentPlace);
    }

    public StringProperty numberValueProperty() {
        return this.firstValueProperty();
    }

    public StringProperty probabilityValueProperty() {
        return this.secondValueProperty();
    }

    public StringProperty TValueProperty() {
        return this.thirdValueProperty();
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

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Binomial;
    }

    @Override
    protected List<String> createValuesNames() {
        return List.of("Number value", "Probability value", "T value");
    }
}
