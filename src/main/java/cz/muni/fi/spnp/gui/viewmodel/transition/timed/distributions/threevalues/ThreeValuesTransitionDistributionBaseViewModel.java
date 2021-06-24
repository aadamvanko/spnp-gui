package cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ThreeValuesTransitionDistributionBaseViewModel<TFirstValue, TSecondValue, TThirdValue>
        extends TransitionDistributionBaseViewModel {

    protected ObjectProperty<TFirstValue> firstValue;
    protected ObjectProperty<TSecondValue> secondValue;
    protected ObjectProperty<TThirdValue> thirdValue;

    public ThreeValuesTransitionDistributionBaseViewModel(TFirstValue firstValue,
                                                          TSecondValue secondValue,
                                                          TThirdValue thirdValue) {
        super(TransitionDistributionType.Constant, null);

        this.firstValue = new SimpleObjectProperty<>(firstValue);
        this.secondValue = new SimpleObjectProperty<>(secondValue);
        this.thirdValue = new SimpleObjectProperty<>(thirdValue);
    }

    public ThreeValuesTransitionDistributionBaseViewModel(FunctionViewModel firstFunction,
                                                          FunctionViewModel secondFunction,
                                                          FunctionViewModel thirdFunction) {
        super(TransitionDistributionType.Functional, null);

        this.functions.set(0, firstFunction);
        this.functions.set(1, secondFunction);
        this.functions.set(2, thirdFunction);
    }

    public ThreeValuesTransitionDistributionBaseViewModel(TFirstValue firstValue,
                                                          TSecondValue secondValue,
                                                          TThirdValue thirdValue,
                                                          PlaceViewModel dependentPlace) {
        super(TransitionDistributionType.PlaceDependent, dependentPlace);

        this.firstValue = new SimpleObjectProperty<>(firstValue);
        this.secondValue = new SimpleObjectProperty<>(secondValue);
        this.thirdValue = new SimpleObjectProperty<>(thirdValue);
    }

    @Override
    protected List<FunctionViewModel> createFunctionsArray() {
        return new ArrayList<>(Collections.nCopies(3, null));
    }

    public ObjectProperty<TFirstValue> firstValueProperty() {
        return firstValue;
    }

    public ObjectProperty<TSecondValue> secondValueProperty() {
        return secondValue;
    }

    public ObjectProperty<TThirdValue> thirdValueProperty() {
        return thirdValue;
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

}
