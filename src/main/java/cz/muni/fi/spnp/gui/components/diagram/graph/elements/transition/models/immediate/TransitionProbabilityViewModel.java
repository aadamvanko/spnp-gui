package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.immediate;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionProbabilityType;
import javafx.beans.property.StringProperty;

public interface TransitionProbabilityViewModel {

    TransitionProbabilityType getEnumType();

    StringProperty representationProperty();

}
