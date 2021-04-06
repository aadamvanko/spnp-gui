package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class ElementViewModel {

    private final StringProperty name = new SimpleStringProperty("element");
    private DiagramViewModel diagramViewModel;

    public StringProperty nameProperty() {
        return name;
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public void setDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }
}
