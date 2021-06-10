package cz.muni.fi.spnp.gui.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class ElementViewModel extends DisplayableViewModel {

    private DiagramViewModel diagramViewModel;

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public void setDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }
}
