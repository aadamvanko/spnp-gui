package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class DiagramViewModel {

    private final ProjectViewModel projectViewModel;
    private final StringProperty name = new SimpleStringProperty();
    private final List<ElementViewModel> elements = new ArrayList<>();

    public DiagramViewModel(ProjectViewModel projectViewModel) {
        this.projectViewModel = projectViewModel;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void addElement(ElementViewModel elementViewModel) {
        elements.add(elementViewModel);
    }
}
