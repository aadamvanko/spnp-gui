package cz.muni.fi.spnp.gui.components.common;

import javafx.scene.Node;

/**
 * Represents UI component which can be placed inside another view.
 */
public interface UIComponent {

    /**
     * @return root view of the component
     */
    Node getRoot();

}
