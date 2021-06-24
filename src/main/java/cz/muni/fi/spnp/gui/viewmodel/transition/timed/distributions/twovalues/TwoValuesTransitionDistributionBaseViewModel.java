package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionBaseViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TwoValuesTransitionDistributionBaseViewModel<TFirstValue, TSecondValue> extends TransitionDistributionBaseViewModel {

    protected ObjectProperty<TFirstValue> firstValue;
    protected ObjectProperty<TSecondValue> secondValue;

    public TwoValuesTransitionDistributionBaseViewModel(TFirstValue firstValue, TSecondValue secondValue) {
        super(TransitionDistributionType.Constant, null);

        this.firstValue = new SimpleObjectProperty<>(firstValue);
        this.secondValue = new SimpleObjectProperty<>(secondValue);
    }

    public TwoValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction, FunctionViewModel secondFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, firstFunction);
        this.functions.set(1, secondFunction);
    }

    public TwoValuesTransitionDistributionBaseViewModel(TFirstValue firstValue, TSecondValue secondValue, PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.firstValue = new SimpleObjectProperty<>(firstValue);
        this.secondValue = new SimpleObjectProperty<>(secondValue);
    }

    @Override
    protected List<FunctionViewModel> createFunctionsArray() {
        return new ArrayList<>(Collections.nCopies(2, null));
    }

    public ObjectProperty<TFirstValue> firstValueProperty() {
        return firstValue;
    }

    public ObjectProperty<TSecondValue> secondValueProperty() {
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
