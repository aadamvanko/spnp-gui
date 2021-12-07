package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.util.converter.DoubleStringConverter;

/**
 * Custom double to string converter.
 */
public class MyDoubleStringConverter extends DoubleStringConverter {

    @Override
    public String toString(Double aDouble) {
        var str = String.format("%.15f", aDouble).replace(',', '.');
        var withoutTrailingZeros = str.contains(".") ? str.replaceAll("0*$", "").replaceAll("\\.$", "") : str;
        return aDouble == null ? "" : withoutTrailingZeros;
    }

}
