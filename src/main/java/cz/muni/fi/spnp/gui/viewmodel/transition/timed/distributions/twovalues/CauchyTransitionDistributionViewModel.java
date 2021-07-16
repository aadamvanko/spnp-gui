package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.CauchyTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;

public class CauchyTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public CauchyTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link CauchyTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param alphaValue alpha value of Cauchy distribution
     * @param betaValue  beta value of Cauchy distribution
     */
    public CauchyTransitionDistributionViewModel(String alphaValue, String betaValue) {
        super(alphaValue, betaValue);
    }

    /**
     * Creates new {@link CauchyTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param alphaValueFunction reference to a function which calculates alpha value of Cauchy distribution
     * @param betaValueFunction  reference to a function which calculates beta value of Cauchy distribution
     */
    public CauchyTransitionDistributionViewModel(FunctionViewModel alphaValueFunction, FunctionViewModel betaValueFunction) {
        super(alphaValueFunction, betaValueFunction);
    }

    /**
     * Creates new {@link CauchyTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param alphaValue     alpha value of Cauchy distribution
     * @param betaValue      beta value of Cauchy distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public CauchyTransitionDistributionViewModel(String alphaValue, String betaValue, PlaceViewModel dependentPlace) {
        super(alphaValue, betaValue, dependentPlace);
    }

    public StringProperty alphaValueProperty() {
        return this.firstValueProperty();
    }

    public StringProperty betaValueProperty() {
        return this.secondValueProperty();
    }

    public void setBetaValue(String betaValue) {
        this.secondValueProperty().set(betaValue);
    }

    public FunctionViewModel getAlphaValueFunction() {
        return this.getFirstFunction();
    }

    public void setAlphaValueFunction(FunctionViewModel alphaValueFunction) {
        this.setFirstFunction(alphaValueFunction);
    }

    public FunctionViewModel getBetaValueFunction() {
        return this.getSecondFunction();
    }

    public void setBetaValueFunction(FunctionViewModel betaValueFunction) {
        this.setSecondFunction(betaValueFunction);
    }

}
