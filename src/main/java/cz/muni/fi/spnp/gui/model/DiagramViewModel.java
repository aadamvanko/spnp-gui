package cz.muni.fi.spnp.gui.model;

import cz.muni.fi.spnp.gui.propertieseditor.PlaceViewModel;

import java.util.HashMap;
import java.util.Map;

public class DiagramViewModel {
    //    private Map<String, ArcViewModel> arcs;
    private final Map<String, PlaceViewModel> places;
//    private Map<String, TransitionViewModel> transitions;

    public DiagramViewModel() {
        places = new HashMap<>();
    }

}
