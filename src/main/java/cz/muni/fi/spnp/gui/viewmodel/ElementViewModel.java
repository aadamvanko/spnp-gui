package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class ElementViewModel extends DisplayableViewModel {

    private final BooleanProperty highlighted;

    public ElementViewModel() {
        this.highlighted = new SimpleBooleanProperty(false);
    }

    public boolean isHighlighted() {
        return highlighted.get();
    }

    public BooleanProperty highlightedProperty() {
        return highlighted;
    }

    @Override
    public String toString() {
        return "ElementViewModel{" + super.toString() +
                "highlighted=" + isHighlighted() +
                '}';
    }

    public void removeFunctionReference(FunctionViewModel removedFunction) {
    }

}
