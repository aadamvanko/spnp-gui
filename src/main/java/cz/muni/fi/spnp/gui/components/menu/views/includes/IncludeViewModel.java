package cz.muni.fi.spnp.gui.components.menu.views.includes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IncludeViewModel {

    private final StringProperty path;

    public IncludeViewModel() {
        this("Format <stdio.h>, \"user.h\", ...");
    }

    public IncludeViewModel(String path) {
        this.path = new SimpleStringProperty(path);
    }

    public StringProperty pathProperty() {
        return path;
    }
}
