package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Custom double property with overridden asObject method, which is used for binding.
 */
public class MySimpleDoubleProperty extends SimpleDoubleProperty {

    private ObjectProperty<Double> asObjectInstance;

    public MySimpleDoubleProperty() {
    }

    public MySimpleDoubleProperty(double v) {
        super(v);
    }

    public MySimpleDoubleProperty(Object o, String s) {
        super(o, s);
    }

    public MySimpleDoubleProperty(Object o, String s, double v) {
        super(o, s, v);
    }

    @Override
    public ObjectProperty<Double> asObject() {
        if (asObjectInstance == null) {
            asObjectInstance = super.asObject();
        }
        return asObjectInstance;
    }

}
