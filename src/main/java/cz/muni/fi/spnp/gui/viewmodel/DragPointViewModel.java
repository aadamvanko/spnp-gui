package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

public class DragPointViewModel extends ElementViewModel {

    private final DoubleProperty positionX = new MySimpleDoubleProperty();
    private final DoubleProperty positionY = new MySimpleDoubleProperty();

    public DragPointViewModel() {
    }

    public DragPointViewModel(double x, double y) {
        positionX.set(x);
        positionY.set(y);
    }

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
}
