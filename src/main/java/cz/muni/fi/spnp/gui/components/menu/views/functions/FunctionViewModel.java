package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FunctionViewModel {
    private final StringProperty name;
    private final ObjectProperty<FunctionType> functionType;
    private final StringProperty body;

    public FunctionViewModel() {
        this("unnamedFunctionViewModel", FunctionType.Other, "emptyBody");
    }

    public FunctionViewModel(String name, FunctionType functionType, String body) {
        this.name = new SimpleStringProperty(name);
        this.functionType = new SimpleObjectProperty<>(functionType);
        this.body = new SimpleStringProperty(body);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<FunctionType> functionTypeProperty() {
        return functionType;
    }

    public StringProperty bodyProperty() {
        return body;
    }
}
