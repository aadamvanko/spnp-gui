package cz.muni.fi.spnp.gui.components.menu.view.variables;

import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

/**
 * View model of the variable.
 */
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public VariableType getKind() {
        return kind.get();
    }

    public ObjectProperty<VariableType> kindProperty() {
        return kind;
    }

    public VariableDataType getType() {
        return type.get();
    }

    public ObjectProperty<VariableDataType> typeProperty() {
        return type;
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableViewModel that = (VariableViewModel) o;
        return name.get().equals(that.name.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.get());
    }
}
