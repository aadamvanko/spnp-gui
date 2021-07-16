package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionBaseViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TwoValuesTransitionDistributionBaseViewModel extends TransitionDistributionBaseViewModel {

    protected SimpleStringProperty firstValue = new SimpleStringProperty("value1");
    protected StringProperty secondValue = new SimpleStringProperty("value2");

    protected TwoValuesTransitionDistributionBaseViewModel() {
    }

    public TwoValuesTransitionDistributionBaseViewModel(String firstValue, String secondValue) {
        super(TransitionDistributionType.Constant, null);

        this.firstValue = new SimpleStringProperty(firstValue);
        this.secondValue = new SimpleStringProperty(secondValue);
    }

    public TwoValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction, FunctionViewModel secondFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, firstFunction);
        this.functions.set(1, secondFunction);
    }

    public TwoValuesTransitionDistributionBaseViewModel(String firstValue, String secondValue, PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.firstValue = new SimpleStringProperty(firstValue);
        this.secondValue = new SimpleStringProperty(secondValue);
    }

    @Override
    protected List<FunctionViewModel> createFunctionsArray() {
        return new ArrayList<>(Collections.nCopies(2, null));
    }

    public String getFirstValue() {
        return firstValue.get();
    }

    public SimpleStringProperty firstValueProperty() {
        return firstValue;
    }

    public String getSecondValue() {
        return secondValue.get();
    }

    public StringProperty secondValueProperty() {
        return secondValue;
    }

    public FunctionViewModel getFirstFunction() {
        return functions.get(0);
    }

    public void setFirstFunction(FunctionViewModel firstFunction) {
        functions.set(0, firstFunction);
    }

    public FunctionViewModel getSecondFunction() {
        return functions.get(1);
    }

    public void setSecondFunction(FunctionViewModel secondFunction) {
        functions.set(1, secondFunction);
    }

}
