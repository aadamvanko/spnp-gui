package cz.muni.fi.spnp.gui.viewmodel.transition.timed;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;

public class TimedTransitionViewModel extends TransitionViewModel {

    private TransitionDistributionViewModel transitionDistribution;

    @Override
    public void removePlaceReference(PlaceViewModel removedPlace) {
        super.removePlaceReference(removedPlace);

        if (transitionDistribution.distributionTypeProperty().get() == TransitionDistributionType.PlaceDependent) {
            if (transitionDistribution.dependentPlaceProperty().get() == removedPlace) {
                transitionDistribution.dependentPlaceProperty().set(null);
            }
        }
    }

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (transitionDistribution.distributionTypeProperty().get() == TransitionDistributionType.Functional) {
            transitionDistribution.getFunctions().forEach(functionProperty -> {
                if (functionProperty.get() == removedFunction) {
                    functionProperty.set(null);
                }
            });
        }
    }

    public TransitionDistributionViewModel getTransitionDistribution() {
        return transitionDistribution;
    }

    public void setTransitionDistribution(TransitionDistributionViewModel transitionDistribution) {
        this.transitionDistribution = transitionDistribution;
    }

}
