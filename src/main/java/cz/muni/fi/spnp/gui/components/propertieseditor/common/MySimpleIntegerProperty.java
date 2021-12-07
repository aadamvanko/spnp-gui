package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Custom integer property with overridden asObject method used for binding.
 */
public class MySimpleIntegerProperty extends SimpleIntegerProperty {

    private ObjectProperty<Integer> asObjectInstance;

    public MySimpleIntegerProperty() {
    }

    public MySimpleIntegerProperty(int i) {
        super(i);
    }

    public MySimpleIntegerProperty(Object o, String s) {
        super(o, s);
    }

    public MySimpleIntegerProperty(Object o, String s, int i) {
        super(o, s, i);
    }

    @Override
    public ObjectProperty<Integer> asObject() {
        if (asObjectInstance == null) {
            asObjectInstance = super.asObject();
        }
        return asObjectInstance;
    }

}
