package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.twovalues;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.TransitionDistributionBaseViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public abstract class TwoValuesTransitionDistributionBaseViewModel extends TransitionDistributionBaseViewModel {

    protected TwoValuesTransitionDistributionBaseViewModel() {
    }

    public TwoValuesTransitionDistributionBaseViewModel(String firstValue, String secondValue) {
        super(TransitionDistributionType.Constant, null);

        this.values.set(0, new SimpleStringProperty(firstValue));
        this.values.set(1, new SimpleStringProperty(secondValue));
    }

    public TwoValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction, FunctionViewModel secondFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, new SimpleObjectProperty<>(firstFunction));
        this.functions.set(1, new SimpleObjectProperty<>(secondFunction));
    }

    public TwoValuesTransitionDistributionBaseViewModel(String firstValue, String secondValue, PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.values.set(0, new SimpleStringProperty(firstValue));
        this.values.set(1, new SimpleStringProperty(secondValue));
    }

    @Override
    protected List<StringProperty> createValues() {
        return createNValues(2);
    }

    @Override
    protected List<String> createValuesNames() {
        return createNValuesNames(2);
    }

    @Override
    protected List<ObjectProperty<FunctionViewModel>> createFunctionsArray() {
        return createNFunctions(2);
    }

    public String getFirstValue() {
        return values.get(0).get();
    }

    public StringProperty firstValueProperty() {
        return values.get(0);
    }

    public String getSecondValue() {
        return values.get(1).get();
    }

    public StringProperty secondValueProperty() {
        return values.get(1);
    }

    public FunctionViewModel getFirstFunction() {
        return functions.get(0).get();
    }

    public void setFirstFunction(FunctionViewModel firstFunction) {
        functions.get(0).set(firstFunction);
    }

    public FunctionViewModel getSecondFunction() {
        return functions.get(1).get();
    }

    public void setSecondFunction(FunctionViewModel secondFunction) {
        functions.get(1).set(secondFunction);
    }

}
