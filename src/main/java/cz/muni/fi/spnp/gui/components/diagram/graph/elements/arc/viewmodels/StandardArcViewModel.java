package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;

import java.util.List;

/**
 * View model of the standard (regular) arc.
 */
public class StandardArcViewModel extends ArcViewModel {

    public StandardArcViewModel() {
    }

    public StandardArcViewModel(String name, ConnectableViewModel fromViewModel, ConnectableViewModel toViewModel, List<DragPointViewModel> dragMarks) {
        super(name, fromViewModel, toViewModel, dragMarks);
    }

}
