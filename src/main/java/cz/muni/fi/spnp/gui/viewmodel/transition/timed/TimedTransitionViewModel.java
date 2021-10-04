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
    private final ObjectProperty<TransitionDistributionViewModel> transitionDistribution;

    public TimedTransitionViewModel() {
        policy = new SimpleObjectProperty<>(PolicyAffectedType.PreemptiveRepeatDifferent);
        affected = new SimpleObjectProperty<>(PolicyAffectedType.PreemptiveResume);
        transitionDistribution = new SimpleObjectProperty(new ConstantTransitionDistributionViewModel());
    }

    @Override
    public void removePlaceReference(PlaceViewModel removedPlace) {
        super.removePlaceReference(removedPlace);

        if (getTransitionDistribution().distributionTypeProperty().get() == TransitionDistributionType.PlaceDependent) {
            if (getTransitionDistribution().dependentPlaceProperty().get() == removedPlace) {
                getTransitionDistribution().dependentPlaceProperty().set(null);
            }
        }
    }

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (getTransitionDistribution().distributionTypeProperty().get() == TransitionDistributionType.Functional) {
            getTransitionDistribution().getFunctions().forEach(functionProperty -> {
                if (functionProperty.get() == removedFunction) {
                    functionProperty.set(null);
                }
            });
        }
    }

    public TransitionDistributionViewModel getTransitionDistribution() {
        return transitionDistribution.get();
    }

    public void setTransitionDistribution(TransitionDistributionViewModel transitionDistribution) {
        this.transitionDistribution.set(transitionDistribution);
    }

    public ObjectProperty<TransitionDistributionViewModel> transitionDistributionProperty() {
        return transitionDistribution;
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
