package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.PoissonTransitionDistribution;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Poisson transition distribution view model for the timed transition.
 */
public class PoissonTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public PoissonTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link PoissonTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param lambdaValue lambda value of Poisson distribution
     * @param tValue      T value of Poisson distribution
     */
    public PoissonTransitionDistributionViewModel(String lambdaValue, String tValue) {
        super(lambdaValue, tValue);
    }

    /**
     * Creates new {@link PoissonTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param lambdaValueFunction reference to a function which calculates lambda value of Poisson distribution
     * @param tValueFunction      reference to a function which calculates T value of Poisson distribution
     */
    public PoissonTransitionDistributionViewModel(FunctionViewModel lambdaValueFunction, FunctionViewModel tValueFunction) {
        super(lambdaValueFunction, tValueFunction);
    }

    /**
     * Creates new {@link PoissonTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param lambdaValue    lambda value of Poisson distribution
     * @param tValue         T value of Poisson distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public PoissonTransitionDistributionViewModel(String lambdaValue, String tValue, PlaceViewModel dependentPlace) {
        super(lambdaValue, tValue, dependentPlace);
    }

    public StringProperty lambdaValueProperty() {
        return firstValueProperty();
    }

    public StringProperty TValueProperty() {
        return secondValueProperty();
    }

    public FunctionViewModel getLambdaValueFunction() {
        return this.getFirstFunction();
    }

    public void setLambdaValueFunction(FunctionViewModel lambdaValueFunction) {
        this.setFirstFunction(lambdaValueFunction);
    }

    public FunctionViewModel getTValueFunction() {
        return this.getSecondFunction();
    }

    public void setTValueFunction(FunctionViewModel tValueFunction) {
        this.setFirstFunction(tValueFunction);
    }

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Poisson;
    }

    @Override
    protected List<String> createValuesNames() {
        return List.of("Lambda value", "T value");
    }

}
