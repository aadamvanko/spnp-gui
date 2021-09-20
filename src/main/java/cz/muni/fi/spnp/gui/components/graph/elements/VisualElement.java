package cz.muni.fi.spnp.gui.components.graph.elements;

import javafx.scene.Node;

public interface VisualElement {

    Node getBottomLayerContainer();

    Node getMiddleLayerContainer();

    Node getTopLayerContainer();

    void addedToParent();

    void removedFromParent();
}
