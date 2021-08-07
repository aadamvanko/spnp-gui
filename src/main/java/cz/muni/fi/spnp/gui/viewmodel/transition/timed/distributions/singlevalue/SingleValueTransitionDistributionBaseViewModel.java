package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionBaseViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public abstract class SingleValueTransitionDistributionBaseViewModel extends TransitionDistributionBaseViewModel {

    protected SingleValueTransitionDistributionBaseViewModel() {
    }

    public SingleValueTransitionDistributionBaseViewModel(String value) {
        super(TransitionDistributionType.Constant, null);

        this.values.set(0, new SimpleStringProperty(value));
    }

    public SingleValueTransitionDistributionBaseViewModel(FunctionViewModel function) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, new SimpleObjectProperty<>(function));
    }

    public SingleValueTransitionDistributionBaseViewModel(String value, PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.values.set(0, new SimpleStringProperty(value));
    }

    @Override
    protected List<StringProperty> createValues() {
        return createNValues(1);
    }

    @Override
    protected List<String> createValuesNames() {
        return createNValuesNames(1);
    }

    @Override
    protected List<ObjectProperty<FunctionViewModel>> createFunctionsArray() {
        return createNFunctions(1);
    }

    public String getValue() {
        return values.get(0).get();
    }

    public StringProperty valueProperty() {
        return values.get(0);
    }

    public FunctionViewModel getFirstFunction() {
        return functions.get(0).get();
    }

    public void setFirstFunction(FunctionViewModel firstFunction) {
        functions.get(0).set(firstFunction);
    }

}
