package cz.muni.fi.spnp.gui.components.propertieseditor;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class DoubleTextField {

    private final TextField textField;

    public DoubleTextField() {
        textField = new TextField();

        TextFormatter<Double> formatter = new TextFormatter<>(new MyDoubleStringConverter(), 0.0, new DoubleFilter());
        textField.setTextFormatter(formatter);
    }

    public TextField getTextField() {
        return textField;
    }

    public TextFormatter<Double> getTextFormatter() {
        return (TextFormatter<Double>) textField.getTextFormatter();
    }

}
