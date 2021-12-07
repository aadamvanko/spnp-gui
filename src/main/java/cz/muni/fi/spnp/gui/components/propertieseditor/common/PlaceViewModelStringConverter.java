package cz.muni.fi.spnp.gui.components.propertieseditor.common;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
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
