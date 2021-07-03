package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.fourvalues;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.models.places.StandardPlace;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.models.visitors.TransitionDistributionFunctionsVisitor;
import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
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

public abstract class FourValuesTransitionDistributionBaseViewModel
        extends TransitionDistributionBaseViewModel {

    protected StringProperty firstValue;
    protected StringProperty secondValue;
    protected StringProperty thirdValue;
    protected StringProperty fourthValue;

    public FourValuesTransitionDistributionBaseViewModel(String firstValue,
                                                         String secondValue,
                                                         String thirdValue,
                                                         String fourthValue) {
        super(TransitionDistributionType.Constant, null);

        this.firstValue = new SimpleStringProperty(firstValue);
        this.secondValue = new SimpleStringProperty(secondValue);
        this.thirdValue = new SimpleStringProperty(thirdValue);
        this.fourthValue = new SimpleStringProperty(fourthValue);
    }

    public FourValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction,
                                                         FunctionViewModel secondFunction,
                                                         FunctionViewModel thirdFunction,
                                                         FunctionViewModel fourthFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, firstFunction);
        this.functions.set(1, secondFunction);
        this.functions.set(2, thirdFunction);
        this.functions.set(3, fourthFunction);
    }

    public FourValuesTransitionDistributionBaseViewModel(String firstValue,
                                                         String secondValue,
                                                         String thirdValue,
                                                         String fourthValue,
                                                         PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.firstValue = new SimpleStringProperty(firstValue);
        this.secondValue = new SimpleStringProperty(secondValue);
        this.thirdValue = new SimpleStringProperty(thirdValue);
        this.fourthValue = new SimpleStringProperty(fourthValue);
    }

    @Override
    protected List<FunctionViewModel> createFunctionsArray() {
        return new ArrayList<>(Collections.nCopies(4, null));
    }

    public StringProperty firstValueProperty() {
        return firstValue;
    }

    public StringProperty secondValueProperty() {
        return secondValue;
    }

    public StringProperty thirdValueProperty() {
        return thirdValue;
    }

    public StringProperty fourthValueProperty() {
        return fourthValue;
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

    public FunctionViewModel getThirdFunction() {
        return functions.get(2);
    }

    public void setThirdFunction(FunctionViewModel thirdFunction) {
        functions.set(2, thirdFunction);
    }

    public FunctionViewModel getFourthFunction() {
        return functions.get(3);
    }

    public void setFourthFunction(FunctionViewModel fourthFunction) {
        functions.set(3, fourthFunction);
    }

}
