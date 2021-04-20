package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class ElementViewModel {

    private DiagramViewModel diagramViewModel;
    private final StringProperty name = new SimpleStringProperty("element");

    public ElementViewModel(String name) {
        this.name.set(name);
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public void setDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    public StringProperty nameProperty() {
        return name;
    }
}
