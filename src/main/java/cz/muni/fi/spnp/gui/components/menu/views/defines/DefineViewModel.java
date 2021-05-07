package cz.muni.fi.spnp.gui.components.menu.views.defines;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DefineViewModel {
    private final StringProperty name;
    private final StringProperty expression;

    public DefineViewModel() {
        this("unnamedDefine", "unknownExpression");
    }

    public DefineViewModel(String name, String expression) {
        this.name = new SimpleStringProperty(name);
        this.expression = new SimpleStringProperty(expression);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty expressionProperty() {
        return expression;
    }
}
