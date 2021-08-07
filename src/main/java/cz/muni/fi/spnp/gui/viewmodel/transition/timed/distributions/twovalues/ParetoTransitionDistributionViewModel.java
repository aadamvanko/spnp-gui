package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.ParetoTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import javafx.beans.property.StringProperty;

import java.util.List;

public class ParetoTransitionDistributionViewModel extends TwoValuesTransitionDistributionBaseViewModel {

    public ParetoTransitionDistributionViewModel() {
    }

    /**
     * Creates new {@link ParetoTransitionDistribution} object with {@link TransitionDistributionType#Constant} distribution type.
     *
     * @param scale scale value of Pareto distribution
     * @param alpha alpha value of Pareto distribution
     */
    public ParetoTransitionDistributionViewModel(String scale, String alpha) {
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
    public ParetoTransitionDistributionViewModel(String scale, String alpha, PlaceViewModel dependentPlace) {
        super(scale, alpha, dependentPlace);
    }

    public StringProperty scaleProperty() {
        return firstValueProperty();
    }

    public StringProperty alphaProperty() {
        return secondValueProperty();
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

    @Override
    public TimedDistributionType getEnumType() {
        return TimedDistributionType.Pareto;
    }

    @Override
    protected List<String> createValuesNames() {
        return List.of("Scale", "Alpha");
    }

}
