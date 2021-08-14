package cz.muni.fi.spnp.gui.viewmodel.transition;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleIntegerProperty;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class TransitionViewModel extends ConnectableViewModel {

    private final IntegerProperty priority = new MySimpleIntegerProperty(0);
    private final ObjectProperty<FunctionViewModel> guardFunction = new SimpleObjectProperty<>();

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (getGuardFunction() == removedFunction) {
            guardFunctionProperty().set(null);
        }
    }

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
