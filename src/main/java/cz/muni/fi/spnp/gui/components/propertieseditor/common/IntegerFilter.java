package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

class IntegerFilter implements UnaryOperator<TextFormatter.Change> {

    private final static Pattern DIGIT_PATTERN = Pattern.compile("\\d*");

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        try {
            Integer.parseInt(change.getControlNewText());
        } catch (NumberFormatException numberFormatException) {
            if (!change.getControlNewText().isEmpty()) {
                return null;
            }
        }

        return DIGIT_PATTERN.matcher(change.getControlNewText()).matches() ? change : null;
    }

}
