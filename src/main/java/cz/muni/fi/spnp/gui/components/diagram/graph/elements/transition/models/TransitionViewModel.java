package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.TransitionOrientation;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Base view model for both types of the transitions.
 */
public abstract class TransitionViewModel extends ConnectableViewModel {

    private final IntegerProperty priority = new MySimpleIntegerProperty(0);
    private final ObjectProperty<FunctionViewModel> guardFunction = new SimpleObjectProperty<>();
    private final ObjectProperty<TransitionOrientation> orientation = new SimpleObjectProperty<>(TransitionOrientation.Vertical);

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

    public TransitionOrientation getOrientation() {
        return orientation.get();
    }

    public ObjectProperty<TransitionOrientation> orientationProperty() {
        return orientation;
    }

}
