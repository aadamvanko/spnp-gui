package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
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

}
