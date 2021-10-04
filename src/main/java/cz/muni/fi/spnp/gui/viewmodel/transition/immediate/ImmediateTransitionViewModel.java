package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ImmediateTransitionViewModel extends TransitionViewModel {

    private final ObjectProperty<TransitionProbabilityViewModel> transitionProbability = new SimpleObjectProperty(new ConstantTransitionProbabilityViewModel());

    @Override
    public void removePlaceReference(PlaceViewModel removedPlace) {
        super.removePlaceReference(removedPlace);

        if (getTransitionProbability() instanceof PlaceDependentTransitionProbabilityViewModel) {
            var placeDependentProbability = (PlaceDependentTransitionProbabilityViewModel) getTransitionProbability();
            if (placeDependentProbability.getDependentPlace() == removedPlace) {
                placeDependentProbability.dependentPlaceProperty().set(null);
            }
        }
    }

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (getTransitionProbability() instanceof FunctionalTransitionProbabilityViewModel) {
            var functionalTransitionProbability = (FunctionalTransitionProbabilityViewModel) getTransitionProbability();
            if (functionalTransitionProbability.getFunction() == removedFunction) {
                functionalTransitionProbability.functionProperty().set(null);
            }
        }
    }

    public TransitionProbabilityViewModel getTransitionProbability() {
        return transitionProbability.get();
    }

    public void setTransitionProbability(TransitionProbabilityViewModel transitionProbability) {
        this.transitionProbability.set(transitionProbability);
    }

    public ObjectProperty<TransitionProbabilityViewModel> transitionProbabilityProperty() {
        return transitionProbability;
    }

}
