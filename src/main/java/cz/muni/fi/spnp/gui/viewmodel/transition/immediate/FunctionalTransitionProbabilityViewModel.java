package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FunctionalTransitionProbabilityViewModel implements TransitionProbabilityViewModel {

    private FunctionViewModel function;

    public FunctionViewModel getFunction() {
        return function;
    }

    public void setFunction(FunctionViewModel function) {
        this.function = function;
    }
}
