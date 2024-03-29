package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Filter used to accept only floating point numbers including the scientific notation.
 */
public class DoubleFilter implements UnaryOperator<TextFormatter.Change> {

    private final static Pattern DIGIT_PATTERN = Pattern.compile("-?\\d+(\\.\\d*)?([eE]-?\\d+)?");

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        return DIGIT_PATTERN.matcher(change.getControlNewText()).matches() ? change : null;
    }

}
