package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;

import java.util.List;

/**
 * View model for the inhibitor arc.
 */
public class InhibitorArcViewModel extends ArcViewModel {

    public InhibitorArcViewModel() {
    }

    public InhibitorArcViewModel(String name, ConnectableViewModel fromViewModel, ConnectableViewModel toViewModel, List<DragPointViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }

}
