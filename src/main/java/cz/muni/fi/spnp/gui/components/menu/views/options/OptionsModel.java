package cz.muni.fi.spnp.gui.components.menu.views.options;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class OptionsModel {

    private final ObjectProperty<OptionsViewType> optionsViewType;

    public OptionsModel() {
        this.optionsViewType = new SimpleObjectProperty<>(OptionsViewType.SIMULATION);
    }

    public OptionsViewType getOptionsViewType() {
        return optionsViewType.get();
    }

    public ObjectProperty<OptionsViewType> optionsViewTypeProperty() {
        return optionsViewType;
    }
}
