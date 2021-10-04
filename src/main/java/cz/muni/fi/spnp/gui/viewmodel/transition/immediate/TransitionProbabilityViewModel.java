package cz.muni.fi.spnp.gui.viewmodel.transition.immediate;

import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import javafx.beans.property.StringProperty;

public interface TransitionProbabilityViewModel {

    TransitionProbabilityType getEnumType();

    StringProperty representationProperty();

}
