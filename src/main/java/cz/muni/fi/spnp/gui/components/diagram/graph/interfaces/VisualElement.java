package cz.muni.fi.spnp.gui.components.diagram.graph.interfaces;

import javafx.scene.Node;

public interface VisualElement {

    Node getBottomLayerContainer();

    Node getMiddleLayerContainer();

    Node getTopLayerContainer();

    void addedToParent();

    void removedFromParent();
}
