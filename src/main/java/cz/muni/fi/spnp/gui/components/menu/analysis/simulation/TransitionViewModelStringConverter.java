package cz.muni.fi.spnp.gui.components.menu.analysis.simulation;

import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.util.StringConverter;

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

