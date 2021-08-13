package cz.muni.fi.spnp.gui.viewmodel;

import java.util.List;

public class InhibitorArcViewModel extends ArcViewModel {

    protected InhibitorArcViewModel() {
    }

    public InhibitorArcViewModel(String name, ConnectableViewModel fromViewModel, ConnectableViewModel toViewModel, List<DragPointViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }

}
