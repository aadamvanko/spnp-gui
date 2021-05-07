package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcDragMark;

import java.util.List;

public class InhibitorArcViewModel extends ArcViewModel {
    public InhibitorArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel, List<ArcDragMarkViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }
}
