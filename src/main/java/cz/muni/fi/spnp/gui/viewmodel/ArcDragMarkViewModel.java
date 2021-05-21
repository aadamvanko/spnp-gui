package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;

public class ArcDragMarkViewModel {
    private final DoubleProperty positionX = new SimpleDoubleProperty();
    private final DoubleProperty positionY = new SimpleDoubleProperty();

    public ArcDragMarkViewModel(double x, double y) {
        positionX.set(x);
        positionY.set(y);
    }

    public DoubleProperty positionXProperty() {
        return positionX;
    }

    public DoubleProperty positionYProperty() {
        return positionY;
    }
}