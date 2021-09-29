package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point2D;

public abstract class ConnectableViewModel extends ElementViewModel {

    private final DoubleProperty positionX = new MySimpleDoubleProperty();
    private final DoubleProperty positionY = new MySimpleDoubleProperty();
    private Point2D preservedShapeCenter = Point2D.ZERO;

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

    public Point2D getPreservedShapeCenter() {
        return preservedShapeCenter;
    }

    public void setPreservedShapeCenter(Point2D preservedShapeCenter) {
        this.preservedShapeCenter = preservedShapeCenter;
    }

    @Override
    public String toString() {
        return "ConnectableViewModel{" + super.toString() +
                "positionX=" + getPositionX() +
                ", positionY=" + getPositionY() +
                '}';
    }
}
