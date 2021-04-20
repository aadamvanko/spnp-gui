package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlaceViewModel extends ConnectableElementViewModel {

    private final IntegerProperty numberOfTokens = new SimpleIntegerProperty(0);

//    public PlaceViewModel() {
//        super();
//    }

    public PlaceViewModel(String name, double x, double y, int numberOfTokens) {
        super(name, x, y);
        this.numberOfTokens.set(numberOfTokens);
    }

    public IntegerProperty numberOfTokensProperty() {
        return numberOfTokens;
    }
}
