package cz.muni.fi.spnp.gui.viewmodel;

import java.util.List;

public class InhibitorArcViewModel extends ArcViewModel {

    protected InhibitorArcViewModel() {
    }

    public InhibitorArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel, List<DragPointViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }

}
