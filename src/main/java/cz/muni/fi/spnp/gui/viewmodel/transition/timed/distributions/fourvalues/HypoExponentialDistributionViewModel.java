package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.fourvalues;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.StringProperty;

public class HypoExponentialDistributionViewModel extends FourValuesTransitionDistributionBaseViewModel {

    public HypoExponentialDistributionViewModel() {
    }

    public HypoExponentialDistributionViewModel(String numberOfStages,
                                                String firstRateValue,
                                                String secondRateValue,
                                                String thirdRateValue) {
        super(numberOfStages, firstRateValue, secondRateValue, thirdRateValue);
        numberOfStagesProperty().set(numberOfStages);
    }

    public HypoExponentialDistributionViewModel(FunctionViewModel numberOfStagesFunction,
                                                FunctionViewModel firstRateValueFunction,
                                                FunctionViewModel secondRateValueFunction,
                                                FunctionViewModel thirdRateValueFunction) {
        super(numberOfStagesFunction, firstRateValueFunction, secondRateValueFunction, thirdRateValueFunction);
    }

    public HypoExponentialDistributionViewModel(String numberOfStages,
                                                String firstRateValue,
                                                String secondRateValue,
                                                String thirdRateValue,
                                                PlaceViewModel dependentPlace) {
        super(numberOfStages, firstRateValue, secondRateValue, thirdRateValue, dependentPlace);
        numberOfStagesProperty().set(numberOfStages);
    }

    public StringProperty numberOfStagesProperty() {
        return firstValueProperty();
    }

    public StringProperty firstRateValueProperty() {
        return secondValueProperty();
    }

    public StringProperty secondRateValueProperty() {
        return thirdValueProperty();
    }

    public StringProperty thirdRateValueProperty() {
        return fourthValueProperty();
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
