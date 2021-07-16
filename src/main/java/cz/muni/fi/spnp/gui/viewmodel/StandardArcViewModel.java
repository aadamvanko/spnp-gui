package cz.muni.fi.spnp.gui.viewmodel;

import java.util.List;

public class StandardArcViewModel extends ArcViewModel {

    protected StandardArcViewModel() {
    }

    public StandardArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel, List<DragPointViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }

}
