package cz.muni.fi.spnp.gui.viewmodel.transition.timed;

import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;

public class TimedTransitionViewModel extends TransitionViewModel {

    private TransitionDistributionViewModel transitionDistribution;

    public TransitionDistributionViewModel getTransitionDistribution() {
        return transitionDistribution;
    }

    public void setTransitionDistribution(TransitionDistributionViewModel transitionDistribution) {
        this.transitionDistribution = transitionDistribution;
    }
}
