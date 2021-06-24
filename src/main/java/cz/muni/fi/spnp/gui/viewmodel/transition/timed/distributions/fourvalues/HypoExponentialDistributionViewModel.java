package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.fourvalues;

import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionVisitor;
import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.FourValuesTransitionDistributionBase;
import cz.muni.fi.spnp.core.transformators.spnp.distributions.HypoExponentialTransitionDistribution;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

public class HypoExponentialDistributionViewModel extends FourValuesTransitionDistributionBaseViewModel<Integer, Double, Double, Double> {

    public HypoExponentialDistributionViewModel(int numberOfStages,
                                                double firstRateValue,
                                                double secondRateValue,
                                                double thirdRateValue) {
        super(numberOfStages, firstRateValue, secondRateValue, thirdRateValue);
        numberOfStagesProperty().set(numberOfStages);
    }

    public HypoExponentialDistributionViewModel(FunctionViewModel numberOfStagesFunction,
                                                FunctionViewModel firstRateValueFunction,
                                                FunctionViewModel secondRateValueFunction,
                                                FunctionViewModel thirdRateValueFunction) {
        super(numberOfStagesFunction, firstRateValueFunction, secondRateValueFunction, thirdRateValueFunction);
    }

    public HypoExponentialDistributionViewModel(int numberOfStages,
                                                double firstRateValue,
                                                double secondRateValue,
                                                double thirdRateValue,
                                                PlaceViewModel dependentPlace) {
        super(numberOfStages, firstRateValue, secondRateValue, thirdRateValue, dependentPlace);
        numberOfStagesProperty().set(numberOfStages);
    }

    public IntegerProperty numberOfStagesProperty() {
        return IntegerProperty.integerProperty(firstValueProperty());
    }

    public DoubleProperty firstRateValueProperty() {
        return DoubleProperty.doubleProperty(secondValueProperty());
    }

    public DoubleProperty secondRateValueProperty() {
        return DoubleProperty.doubleProperty(thirdValueProperty());
    }

    public DoubleProperty thirdRateValueProperty() {
        return DoubleProperty.doubleProperty(fourthValueProperty());
    }

    public FunctionViewModel getNumberOfStagesFunction() {
        return this.getFirstFunction();
    }

    public void setNumberOfStagesFunction(FunctionViewModel numberOfStagesFunction) {
        this.setFirstFunction(numberOfStagesFunction);
    }

    public FunctionViewModel getFirstRateValueFunction() {
        return this.getSecondFunction();
    }

    public void setFirstRateValueFunction(FunctionViewModel firstRateValueFunction) {
        this.setSecondFunction(firstRateValueFunction);
    }

    public FunctionViewModel getSecondRateValueFunction() {
        return this.getThirdFunction();
    }

    public void setSecondRateValueFunction(FunctionViewModel secondRateValueFunction) {
        this.setThirdFunction(secondRateValueFunction);
    }

    public FunctionViewModel getThirdRateValueFunction() {
        return this.getFourthFunction();
    }

    public void setThirdRateValueFunction(FunctionViewModel thirdRateValueFunction) {
        this.setFourthFunction(thirdRateValueFunction);
    }
}
