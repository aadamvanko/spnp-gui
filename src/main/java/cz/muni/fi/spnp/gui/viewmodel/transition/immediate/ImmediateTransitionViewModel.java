package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.viewmodel.TransitionProbabilityType;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.beans.property.*;

public class ImmediateTransitionViewModel extends TransitionViewModel {

    private TransitionProbabilityViewModel transitionProbability;

    public TransitionProbabilityViewModel getTransitionProbability() {
        return transitionProbability;
    }

    public void setTransitionProbability(TransitionProbabilityViewModel transitionProbability) {
        this.transitionProbability = transitionProbability;
    }
}
