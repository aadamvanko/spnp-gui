package cz.muni.fi.spnp.gui.viewmodel.transition.timed;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.elements.PolicyAffectedType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ConstantTransitionDistributionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TimedTransitionViewModel extends TransitionViewModel {

    private final ObjectProperty<PolicyAffectedType> policy;
    private final ObjectProperty<PolicyAffectedType> affected;
    private TransitionDistributionViewModel transitionDistribution;

    public TimedTransitionViewModel() {
        policy = new SimpleObjectProperty<>(PolicyAffectedType.PreemptiveRepeatDifferent);
        affected = new SimpleObjectProperty<>(PolicyAffectedType.PreemptiveResume);
        transitionDistribution = new ConstantTransitionDistributionViewModel();
    }

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

    public PolicyAffectedType getPolicy() {
        return policy.get();
    }

    public ObjectProperty<PolicyAffectedType> policyProperty() {
        return policy;
    }

    public PolicyAffectedType getAffected() {
        return affected.get();
    }

    public ObjectProperty<PolicyAffectedType> affectedProperty() {
        return affected;
    }

}
