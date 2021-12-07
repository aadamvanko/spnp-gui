package cz.muni.fi.spnp.gui.components.menu.analysis.simulation;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionViewModel;
import javafx.util.StringConverter;

/**
 * Converts the transition view model to the string representation and vice versa.
 */
public class TransitionViewModelStringConverter extends StringConverter<TransitionViewModel> {

    private final DiagramViewModel diagramViewModel;

    public TransitionViewModelStringConverter(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    @Override
    public String toString(TransitionViewModel transitionViewModel) {
        if (transitionViewModel == null) {
            return null;
        }
        return transitionViewModel.getName();
    }

    @Override
    public TransitionViewModel fromString(String name) {
        return diagramViewModel.getTransitionByName(name);
    }

}

