package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.fourvalues;

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
 * Base class for four values transition distribution view model for timed transition.
 */
public abstract class FourValuesTransitionDistributionBaseViewModel extends TransitionDistributionBaseViewModel {

    public FourValuesTransitionDistributionBaseViewModel() {
    }

    public FourValuesTransitionDistributionBaseViewModel(String firstValue,
                                                         String secondValue,
                                                         String thirdValue,
                                                         String fourthValue) {
        super(TransitionDistributionType.Constant, null);

        this.values.set(0, new SimpleStringProperty(firstValue));
        this.values.set(1, new SimpleStringProperty(secondValue));
        this.values.set(2, new SimpleStringProperty(thirdValue));
        this.values.set(3, new SimpleStringProperty(fourthValue));
    }

    public FourValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction,
                                                         FunctionViewModel secondFunction,
                                                         FunctionViewModel thirdFunction,
                                                         FunctionViewModel fourthFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, new SimpleObjectProperty<>(firstFunction));
        this.functions.set(1, new SimpleObjectProperty<>(secondFunction));
        this.functions.set(2, new SimpleObjectProperty<>(thirdFunction));
        this.functions.set(3, new SimpleObjectProperty<>(fourthFunction));
    }

    public FourValuesTransitionDistributionBaseViewModel(String firstValue,
                                                         String secondValue,
                                                         String thirdValue,
                                                         String fourthValue,
                                                         PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.values.set(0, new SimpleStringProperty(firstValue));
        this.values.set(1, new SimpleStringProperty(secondValue));
        this.values.set(2, new SimpleStringProperty(thirdValue));
        this.values.set(3, new SimpleStringProperty(fourthValue));
    }

    @Override
    protected List<StringProperty> createValues() {
        return createNValues(4);
    }

    @Override
    protected List<String> createValuesNames() {
        return createNValuesNames(4);
    }

    @Override
    protected List<ObjectProperty<FunctionViewModel>> createFunctionsArray() {
        return createNFunctions(4);
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

    public String getFourthValue() {
        return values.get(3).get();
    }

    public StringProperty fourthValueProperty() {
        return values.get(3);
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

    public FunctionViewModel getFourthFunction() {
        return functions.get(3).get();
    }

    public void setFourthFunction(FunctionViewModel fourthFunction) {
        functions.get(3).set(fourthFunction);
    }

}
