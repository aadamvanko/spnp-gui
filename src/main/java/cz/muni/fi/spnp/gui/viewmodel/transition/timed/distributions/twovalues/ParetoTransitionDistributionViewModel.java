package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.ParetoTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;

public class ParetoTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel<Double, Double> {

    /**
     * Creates new {@link ParetoTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param scale scale value of Pareto distribution
     * @param alpha alpha value of Pareto distribution
     */
    public ParetoTransitionDistributionViewModel(Double scale, Double alpha) {
        super(scale, alpha);
    }

    /**
     * Creates new {@link ParetoTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param scaleFunction reference to a function which calculates scale value of Pareto distribution
     * @param alphaFunction reference to a function which calculates alpha value of Pareto distribution
     */
    public ParetoTransitionDistributionViewModel(FunctionViewModel scaleFunction, FunctionViewModel alphaFunction) {
        super(scaleFunction, alphaFunction);
    }

    /**
     * Creates new {@link ParetoTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param scale          scale value of Pareto distribution
     * @param alpha          alpha value of Pareto distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public ParetoTransitionDistributionViewModel(Double scale, Double alpha, PlaceViewModel dependentPlace) {
        super(scale, alpha, dependentPlace);
    }

    public DoubleProperty scaleProperty() {
        return DoubleProperty.doubleProperty(firstValueProperty());
    }

    public DoubleProperty alphaProperty() {
        return DoubleProperty.doubleProperty(secondValueProperty());
    }

    public FunctionViewModel getScaleFunction() {
        return this.getFirstFunction();
    }

    public void setScaleFunction(FunctionViewModel scaleFunction) {
        this.setFirstFunction(scaleFunction);
    }

    public FunctionViewModel getAlphaFunction() {
        return this.getSecondFunction();
    }

    public void setAlphaFunction(FunctionViewModel alphaFunction) {
        this.setSecondFunction(alphaFunction);
    }

}
