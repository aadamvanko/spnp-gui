package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ConnectableElementViewModel extends ElementViewModel {

    private final DoubleProperty positionX = new SimpleDoubleProperty();
    private final DoubleProperty positionY = new SimpleDoubleProperty();

    public DoubleProperty positionXProperty() {
        return positionX;
    }

    public DoubleProperty positionYProperty() {
        return positionY;
    }
}
