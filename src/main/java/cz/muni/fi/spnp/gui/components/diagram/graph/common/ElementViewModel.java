package cz.muni.fi.spnp.gui.components.diagram.graph.common;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
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

    public void removePlaceReference(PlaceViewModel removedPlace) {
    }

    public void removeFunctionReference(FunctionViewModel removedFunction) {
    }

}
