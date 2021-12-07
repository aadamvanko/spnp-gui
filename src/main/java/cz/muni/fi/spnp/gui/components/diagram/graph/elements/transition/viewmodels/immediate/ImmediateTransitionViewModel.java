package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * View model of the immediate transition.
 */
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
