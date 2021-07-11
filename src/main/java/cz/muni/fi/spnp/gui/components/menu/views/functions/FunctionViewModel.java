package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class FunctionViewModel {
    private final StringProperty name;
    private final ObjectProperty<FunctionType> functionType;
    private final StringProperty body;
    private final ObjectProperty<FunctionReturnType> returnType;
    private final Boolean required;

    public FunctionViewModel() {
        this("unnamedFunctionViewModel", FunctionType.Other, "emptyBody", FunctionReturnType.VOID, false);
    }

    public FunctionViewModel(String name, FunctionType functionType, String body, FunctionReturnType returnType, Boolean required) {
        this.name = new SimpleStringProperty(name);
        this.functionType = new SimpleObjectProperty<>(functionType);
        this.body = new SimpleStringProperty(body);
        this.returnType = new SimpleObjectProperty<>(returnType);
        this.required = required;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public FunctionType getFunctionType() {
        return functionType.get();
    }

    public ObjectProperty<FunctionType> functionTypeProperty() {
        return functionType;
    }

    public String getBody() {
        return body.get();
    }

    public StringProperty bodyProperty() {
        return body;
    }

    public FunctionReturnType getReturnType() {
        return returnType.get();
    }

    public ObjectProperty<FunctionReturnType> returnTypeProperty() {
        return returnType;
    }

    public Boolean getRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionViewModel that = (FunctionViewModel) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return "FunctionViewModel{" +
                "name=" + name.get() +
                '}';
    }
}
