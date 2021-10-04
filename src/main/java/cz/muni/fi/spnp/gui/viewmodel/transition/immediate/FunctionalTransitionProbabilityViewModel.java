package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FunctionalTransitionProbabilityViewModel extends TransitionProbabilityViewModelBase {

    private final ObjectProperty<FunctionViewModel> function = new SimpleObjectProperty<>();

    public FunctionalTransitionProbabilityViewModel() {
        function.addListener((observable, oldValue, newValue) -> updateRepresentation());
        updateRepresentation();
    }

    @Override
    protected String generateRepresentation() {
        var functionName = getFunction() == null ? "null" : getFunction().getName();
        return String.format("F(%s)", functionName);
    }

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
