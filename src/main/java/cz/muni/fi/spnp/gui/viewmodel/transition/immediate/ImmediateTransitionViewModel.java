package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;

public class ImmediateTransitionViewModel extends TransitionViewModel {

    private TransitionProbabilityViewModel transitionProbability = new ConstantTransitionProbabilityViewModel();

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (transitionProbability instanceof FunctionalTransitionProbabilityViewModel) {
            var functionalTransitionProbability = (FunctionalTransitionProbabilityViewModel) transitionProbability;
            if (functionalTransitionProbability.getFunction() == removedFunction) {
                functionalTransitionProbability.functionProperty().set(null);
            }
        }
    }

    public TransitionProbabilityViewModel getTransitionProbability() {
        return transitionProbability;
    }

    public void setTransitionProbability(TransitionProbabilityViewModel transitionProbability) {
        this.transitionProbability = transitionProbability;
    }

}
