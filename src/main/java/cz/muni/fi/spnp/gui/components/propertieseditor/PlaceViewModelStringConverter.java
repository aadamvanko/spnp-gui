package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.util.StringConverter;

public class PlaceViewModelStringConverter extends StringConverter<PlaceViewModel> {

    private final DiagramViewModel diagramViewModel;

    public PlaceViewModelStringConverter(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    @Override
    public String toString(PlaceViewModel placeViewModel) {
        if (placeViewModel == null) {
            return null;
        }
        return placeViewModel.getName();
    }

    @Override
    public PlaceViewModel fromString(String name) {
        return diagramViewModel.getPlaceByName(name);
    }

}
