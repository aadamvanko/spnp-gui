package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlaceViewModel extends ElementViewModel {

    private final StringProperty name = new SimpleStringProperty("place1");
    private final IntegerProperty numberOfTokens = new SimpleIntegerProperty(0);


    public IntegerProperty numberOfTokensProperty() {
        return numberOfTokens;
    }
}
