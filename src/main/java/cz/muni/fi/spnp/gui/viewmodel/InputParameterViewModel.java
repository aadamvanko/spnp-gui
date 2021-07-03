package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InputParameterViewModel {

    private final StringProperty name;
    private final ObjectProperty<VariableDataType> type;
    private final StringProperty userPromptText;

    public InputParameterViewModel() {
        this("unnamedInputParameter", VariableDataType.DOUBLE, "no user prompt text");
    }

    public InputParameterViewModel(String name, VariableDataType type, String userPromptText) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleObjectProperty<>(type);
        this.userPromptText = new SimpleStringProperty(userPromptText);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<VariableDataType> typeProperty() {
        return type;
    }

    public StringProperty userPromptTextProperty() {
        return userPromptText;
    }
}
