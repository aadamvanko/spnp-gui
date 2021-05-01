package cz.muni.fi.spnp.gui.components.menu.views.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FunctionViewModel {
    private final StringProperty name = new SimpleStringProperty("");
    private final ObjectProperty<FunctionType> functionType = new SimpleObjectProperty<>(FunctionType.Generic);
    private final StringProperty body = new SimpleStringProperty("");

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
