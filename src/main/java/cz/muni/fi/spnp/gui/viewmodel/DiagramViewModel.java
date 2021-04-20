package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DiagramViewModel {

    private final Notifications notifications;
    private final ProjectViewModel projectViewModel;
    private final StringProperty name = new SimpleStringProperty();
    private final ObservableList<ElementViewModel> elements;
    private final ObservableList<Define> defines;
    private final ObservableList<Function> functions;

    public DiagramViewModel(Notifications notifications, ProjectViewModel projectViewModel) {
        this.notifications = notifications;
        this.projectViewModel = projectViewModel;

        this.elements = FXCollections.observableArrayList();
        this.defines = FXCollections.observableArrayList();
        this.functions = FXCollections.observableArrayList();
    }

    public DiagramViewModel(Notifications notifications, ProjectViewModel projectViewModel, List<ElementViewModel> elements, List<Define> defines, List<Function> functions) {
        this.notifications = notifications;
        this.projectViewModel = projectViewModel;
        this.elements = FXCollections.observableArrayList(elements);
        this.defines = FXCollections.observableArrayList(defines);
        this.functions = FXCollections.observableArrayList(functions);
    }

    public void addElement(ElementViewModel elementViewModel) {
        elements.add(elementViewModel);
        notifications.newElementAdded(elementViewModel);
    }

    public ProjectViewModel getProject() {
        return projectViewModel;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<Define> getDefines() {
        return defines;
    }

    public ObservableList<Function> getFunctions() {
        return functions;
    }

    public ObservableList<ElementViewModel> getElements() {
        return elements;
    }
}
