package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VariableViewModel {

    private final StringProperty name;
    private final ObjectProperty<VariableType> kind; // swapped names for types and kinds
    private final ObjectProperty<VariableDataType> type;
    private final StringProperty value;

    public VariableViewModel() {
        this("unnamedVariable", VariableType.Global, VariableDataType.INT, "0");
    }

    public VariableViewModel(String name, VariableType kind, VariableDataType type, String value) {
        this.name = new SimpleStringProperty(name);
        this.kind = new SimpleObjectProperty<>(kind);
        this.type = new SimpleObjectProperty<>(type);
        this.value = new SimpleStringProperty(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<VariableType> kindProperty() {
        return kind;
    }

    public ObjectProperty<VariableDataType> typeProperty() {
        return type;
    }

    public StringProperty valueProperty() {
        return value;
    }
}
