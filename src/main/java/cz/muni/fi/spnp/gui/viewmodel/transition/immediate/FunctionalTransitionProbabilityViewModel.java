package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FunctionalTransitionProbabilityViewModel implements TransitionProbabilityViewModel {

    private final ObjectProperty<FunctionViewModel> function = new SimpleObjectProperty<>();

    public FunctionViewModel getFunction() {
        return function.get();
    }

    public ObjectProperty<FunctionViewModel> functionProperty() {
        return function;
    }

    @Override
    public TransitionProbabilityType getEnumType() {
        return TransitionProbabilityType.Functional;
    }

}
