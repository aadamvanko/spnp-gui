package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.core.models.places.Place;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionBaseViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SingleValueTransitionDistributionBaseViewModel<TValue> extends TransitionDistributionBaseViewModel {

    private ObjectProperty<TValue> value;

    public SingleValueTransitionDistributionBaseViewModel(TValue value) {
        super(TransitionDistributionType.Constant, null);

        this.value = new SimpleObjectProperty<>(value);
    }

    public SingleValueTransitionDistributionBaseViewModel(FunctionViewModel function) {
        super(TransitionDistributionType.Functional, null);
        this.functions.set(0, function);
    }

    public SingleValueTransitionDistributionBaseViewModel(TValue value, PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.value = new SimpleObjectProperty<>(value);
    }

    @Override
    protected List<FunctionViewModel> createFunctionsArray() {
        return new ArrayList<>(Collections.nCopies(1, null));
    }

    public ObjectProperty<TValue> valueProperty() {
        return value;
    }

    public FunctionViewModel getFirstFunction() {
        return functions.get(0);
    }

    public void setFirstFunction(FunctionViewModel function1) {
        functions.set(0, function1);
    }
}
