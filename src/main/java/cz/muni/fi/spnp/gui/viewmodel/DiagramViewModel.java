package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DiagramViewModel {

    private final ProjectViewModel projectViewModel;
    private final StringProperty name = new SimpleStringProperty();
    private final List<ElementViewModel> elements = new ArrayList<>();
    private final ObservableList<Define> defines = FXCollections.observableArrayList();
    private final ObservableList<Function> functions = FXCollections.observableArrayList();

    public DiagramViewModel(ProjectViewModel projectViewModel) {
        this.projectViewModel = projectViewModel;

        defines.add(new Define("MAX_SIZE", "10"));
        defines.add(new Define("MIN_SIZE", "-4"));

        functions.add(new Function("function1", FunctionType.Guard, "int x = 3;"));
        functions.add(new Function("function2", FunctionType.Generic, "double d = 10;"));
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void addElement(ElementViewModel elementViewModel) {
        elements.add(elementViewModel);
    }

    public ObservableList<Define> getDefines() {
        return defines;
    }

    public ObservableList<Function> getFunctions() {
        return functions;
    }
}
