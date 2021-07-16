package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.UniformTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;

public class UniformTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public UniformTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link UniformTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param lowerBound lower bound value of uniform distribution
     * @param upperBound upper bound value of uniform distribution
     */
    public UniformTransitionDistributionViewModel(String lowerBound, String upperBound) {
        super(lowerBound, upperBound);
    }

    /**
     * Creates new {@link UniformTransitionDistribution} object with {@link TransitionDistributionType#Functional} distribution type.
     *
     * @param lowerBoundFunction reference to a function which calculates lower bound value of uniform distribution
     * @param upperBoundFunction reference to a function which calculates upper bound value of uniform distribution
     */
    public UniformTransitionDistributionViewModel(FunctionViewModel lowerBoundFunction, FunctionViewModel upperBoundFunction) {
        super(lowerBoundFunction, upperBoundFunction);
    }

    /**
     * Creates new {@link UniformTransitionDistribution} object with {@link TransitionDistributionType#PlaceDependent} distribution type.
     *
     * @param lowerBound     lower bound value of uniform distribution
     * @param upperBound     upper bound value of uniform distribution
     * @param dependentPlace reference to a {@link StandardPlace} object which is used for distribution
     */
    public UniformTransitionDistributionViewModel(String lowerBound, String upperBound, PlaceViewModel dependentPlace) {
        super(lowerBound, upperBound, dependentPlace);
    }

    public StringProperty lowerBoundProperty() {
        return firstValueProperty();
    }

    public StringProperty upperBoundProperty() {
        return secondValueProperty();
    }

    public FunctionViewModel getLowerBoundFunction() {
        return this.getFirstFunction();
    }

    public void setLowerBoundFunction(FunctionViewModel lowerBoundFunction) {
        this.setFirstFunction(lowerBoundFunction);
    }

    public FunctionViewModel getUpperBoundFunction() {
        return this.getSecondFunction();
    }

    public void setUpperBoundFunction(FunctionViewModel upperBoundFunction) {
        this.setSecondFunction(upperBoundFunction);
    }

}
