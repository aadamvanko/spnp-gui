package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Custom text field accepting only integers.
 */
public class IntegerTextField {

    private final TextField textField;

    public IntegerTextField() {
        textField = new TextField();

        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, new IntegerFilter());
        textField.setTextFormatter(formatter);
    }

    public TextField getTextField() {
        return textField;
    }

    public TextFormatter<Integer> getTextFormatter() {
        return (TextFormatter<Integer>) textField.getTextFormatter();
    }

}
