package cz.muni.fi.spnp.gui.components.diagram.graph.elements.place;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaceViewModel extends ConnectableViewModel {

    private final StringProperty numberOfTokens = new SimpleStringProperty("0");

    public String getNumberOfTokens() {
        return numberOfTokens.get();
    }

    public StringProperty numberOfTokensProperty() {
        return numberOfTokens;
    }
}
