package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

public abstract class ConnectableViewModel extends ElementViewModel {

    private final DoubleProperty positionX = new MySimpleDoubleProperty();
    private final DoubleProperty positionY = new MySimpleDoubleProperty();

    public double getPositionX() {
        return positionX.get();
    }

    public DoubleProperty positionXProperty() {
        return positionX;
    }

    public double getPositionY() {
        return positionY.get();
    }

    public DoubleProperty positionYProperty() {
        return positionY;
    }

    @Override
    public String toString() {
        return "ConnectableViewModel{" + super.toString() +
                "positionX=" + getPositionX() +
                ", positionY=" + getPositionY() +
                '}';
    }
}
