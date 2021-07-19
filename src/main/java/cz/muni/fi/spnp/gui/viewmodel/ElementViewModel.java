package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class ElementViewModel extends DisplayableViewModel {

    private final BooleanProperty highlighted;

    public ElementViewModel() {
        highlighted = new SimpleBooleanProperty(false);
    }

    public boolean isHighlighted() {
        return highlighted.get();
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted.set(highlighted);
    }

    public BooleanProperty highlightedProperty() {
        return highlighted;
    }
}
