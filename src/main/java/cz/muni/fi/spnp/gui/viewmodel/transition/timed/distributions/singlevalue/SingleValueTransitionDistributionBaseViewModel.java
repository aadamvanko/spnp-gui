package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.core.models.places.Place;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionBaseViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SingleValueTransitionDistributionBaseViewModel extends TransitionDistributionBaseViewModel {

    private StringProperty value;

    public SingleValueTransitionDistributionBaseViewModel(String value) {
        super(TransitionDistributionType.Constant, null);

        this.value = new SimpleStringProperty(value);
    }

    public SingleValueTransitionDistributionBaseViewModel(FunctionViewModel function) {
        super(TransitionDistributionType.Functional, null);
        this.functions.set(0, function);
    }

    public SingleValueTransitionDistributionBaseViewModel(String value, PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.value = new SimpleStringProperty(value);
    }

    @Override
    protected List<FunctionViewModel> createFunctionsArray() {
        return new ArrayList<>(Collections.nCopies(1, null));
    }

    public StringProperty valueProperty() {
        return value;
    }

    public FunctionViewModel getFirstFunction() {
        return functions.get(0);
    }

    public void setFirstFunction(FunctionViewModel function1) {
        functions.set(0, function1);
    }
}
