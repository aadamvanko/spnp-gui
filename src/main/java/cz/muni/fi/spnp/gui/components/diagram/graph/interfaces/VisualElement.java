package cz.muni.fi.spnp.gui.components.diagram.graph.interfaces;

import javafx.scene.Node;

/**
 * Represents visual element that can be added to the graph.
 */
public interface VisualElement {

    /**
     * @return root control for the bottom layer of the visual element
     */
    Node getBottomLayerContainer();

    /**
     * @return root control for the middle layer of the visual element
     */
    Node getMiddleLayerContainer();

    /**
     * @return root control for the top layer of the visual element
     */
    Node getTopLayerContainer();

    /**
     * Called when added to the graph.
     */
    void addedToParent();

    /**
     * Called when removed from the graph.
     */
    void removedFromParent();

}
