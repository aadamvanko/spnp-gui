package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.immediate;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionProbabilityType;
import javafx.beans.property.StringProperty;

/**
 * Allows different transition probability types.
 */
public interface TransitionProbabilityViewModel {

    /**
     * @return transition probability type
     */
    TransitionProbabilityType getEnumType();

    /**
     * @return StringProperty containing the textual representation (only type due to length)
     */
    StringProperty representationProperty();

}
