package cz.muni.fi.spnp.gui.components.menu.view.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * View model of the function.
 */
public class FunctionViewModel extends DisplayableViewModel {

    private final ObjectProperty<FunctionType> functionType;
    private final StringProperty body;
    private final ObjectProperty<FunctionReturnType> returnType;
    private Boolean required;
    private Boolean visible;

    public FunctionViewModel() {
        this("unnamedFunctionViewModel", FunctionType.Other, "// empty body", FunctionReturnType.VOID, false, true);
    }

    public FunctionViewModel(String name, FunctionType functionType, String body, FunctionReturnType returnType, Boolean required, Boolean visible) {
        super(name);
        this.functionType = new SimpleObjectProperty<>(functionType);
        this.body = new SimpleStringProperty(body);
        this.returnType = new SimpleObjectProperty<>(returnType);
        this.required = required;
        this.visible = visible;
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

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
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
                "name=" + getName() +
                '}';
    }

}
