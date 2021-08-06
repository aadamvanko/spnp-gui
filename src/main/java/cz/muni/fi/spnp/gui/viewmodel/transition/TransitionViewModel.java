package cz.muni.fi.spnp.gui.viewmodel.transition;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class TransitionViewModel extends ConnectableViewModel {

    private final IntegerProperty priority = new SimpleIntegerProperty(0);
    private final ObjectProperty<FunctionViewModel> guardFunction = new SimpleObjectProperty<>();

    public int getPriority() {
        return priority.get();
    }

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public FunctionViewModel getGuardFunction() {
        return guardFunction.get();
    }

    public ObjectProperty<FunctionViewModel> guardFunctionProperty() {
        return guardFunction;
    }


}
