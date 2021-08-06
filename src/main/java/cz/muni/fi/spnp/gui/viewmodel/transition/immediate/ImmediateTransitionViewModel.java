package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;

public class ImmediateTransitionViewModel extends TransitionViewModel {

    private TransitionProbabilityViewModel transitionProbability = new ConstantTransitionProbabilityViewModel();

    public TransitionProbabilityViewModel getTransitionProbability() {
        return transitionProbability;
    }

    public void setTransitionProbability(TransitionProbabilityViewModel transitionProbability) {
        this.transitionProbability = transitionProbability;
    }
}
