package cz.muni.fi.spnp.gui.components.menu.view.defines;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getExpression() {
        return expression.get();
    }

    public StringProperty expressionProperty() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefineViewModel that = (DefineViewModel) o;
        return name.get().equals(that.name.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.get());
    }
}
