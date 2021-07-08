package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
