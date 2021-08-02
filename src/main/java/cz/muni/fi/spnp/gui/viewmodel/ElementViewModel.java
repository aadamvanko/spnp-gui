package cz.muni.fi.spnp.gui.viewmodel;

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

}
