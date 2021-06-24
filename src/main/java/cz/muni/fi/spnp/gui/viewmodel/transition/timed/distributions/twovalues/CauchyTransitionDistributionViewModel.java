package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionVisitor;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.CauchyTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;

public class CauchyTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel<Double, Double> {

    /**
     * Creates new {@link CauchyTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param alphaValue alpha value of Cauchy distribution
     * @param betaValue  beta value of Cauchy distribution
     */
    public CauchyTransitionDistributionViewModel(Double alphaValue, Double betaValue) {
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
    public CauchyTransitionDistributionViewModel(Double alphaValue, Double betaValue, PlaceViewModel dependentPlace) {
        super(alphaValue, betaValue, dependentPlace);
    }

    public DoubleProperty alphaValueProperty() {
        return DoubleProperty.doubleProperty(this.firstValueProperty());
    }

    public DoubleProperty betaValueProperty() {
        return DoubleProperty.doubleProperty(this.secondValueProperty());
    }

    public void setBetaValue(double betaValue) {
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
