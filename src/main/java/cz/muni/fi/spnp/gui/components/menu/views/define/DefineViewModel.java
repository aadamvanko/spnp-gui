package cz.muni.fi.spnp.gui.components.menu.views.define;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DefineViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty expression = new SimpleStringProperty("");

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty expressionProperty() {
        return expression;
    }
}
