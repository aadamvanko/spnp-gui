package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlaceViewModel extends ElementViewModel {

    private final IntegerProperty numberOfTokens = new SimpleIntegerProperty(0);

    public IntegerProperty numberOfTokensProperty() {
        return numberOfTokens;
    }
}
