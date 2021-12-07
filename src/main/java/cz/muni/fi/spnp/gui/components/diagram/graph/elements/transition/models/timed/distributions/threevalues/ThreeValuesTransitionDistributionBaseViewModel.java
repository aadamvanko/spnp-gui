package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.threevalues;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.TransitionDistributionBaseViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Base class for three values transition distribution view model for timed transition.
 */
public abstract class ThreeValuesTransitionDistributionBaseViewModel extends TransitionDistributionBaseViewModel {

    protected ThreeValuesTransitionDistributionBaseViewModel() {
    }

    public ThreeValuesTransitionDistributionBaseViewModel(String firstValue,
                                                          String secondValue,
                                                          String thirdValue) {
        super(TransitionDistributionType.Constant, null);

        this.values.set(0, new SimpleStringProperty(firstValue));
        this.values.set(1, new SimpleStringProperty(secondValue));
        this.values.set(2, new SimpleStringProperty(thirdValue));
    }

    public ThreeValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction,
                                                          FunctionViewModel secondFunction,
                                                          FunctionViewModel thirdFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, new SimpleObjectProperty<>(firstFunction));
        this.functions.set(1, new SimpleObjectProperty<>(secondFunction));
        this.functions.set(2, new SimpleObjectProperty<>(thirdFunction));
    }

    public ThreeValuesTransitionDistributionBaseViewModel(String firstValue,
                                                          String secondValue,
                                                          String thirdValue,
                                                          PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.values.set(0, new SimpleStringProperty(firstValue));
        this.values.set(1, new SimpleStringProperty(secondValue));
        this.values.set(2, new SimpleStringProperty(thirdValue));
    }

    @Override
    protected List<StringProperty> createValues() {
        return createNValues(3);
    }

    @Override
    protected List<String> createValuesNames() {
        return createNValuesNames(3);
    }

    @Override
    protected List<ObjectProperty<FunctionViewModel>> createFunctionsArray() {
        return createNFunctions(3);
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

    public String getThirdValue() {
        return values.get(2).get();
    }

    public StringProperty thirdValueProperty() {
        return values.get(2);
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

    public FunctionViewModel getThirdFunction() {
        return functions.get(2).get();
    }

    public void setThirdFunction(FunctionViewModel thirdFunction) {
        functions.get(2).set(thirdFunction);
    }

}
