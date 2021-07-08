package cz.muni.fi.spnp.gui.viewmodel.transition;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.mappers.FunctionMapper;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.beans.property.*;

public class TransitionViewModel extends ConnectableViewModel {

    private final IntegerProperty priority = new SimpleIntegerProperty();
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
