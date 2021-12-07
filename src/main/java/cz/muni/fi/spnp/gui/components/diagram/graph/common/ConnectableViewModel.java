package cz.muni.fi.spnp.gui.components.diagram.graph.common;

import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

/**
 * Represents the visual elements that can be connected via arcs.
 */
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
