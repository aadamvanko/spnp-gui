package cz.muni.fi.spnp.gui.viewmodel;

import java.util.List;

public class StandardArcViewModel extends ArcViewModel {
    public StandardArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel, List<ArcDragMarkViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }
}
