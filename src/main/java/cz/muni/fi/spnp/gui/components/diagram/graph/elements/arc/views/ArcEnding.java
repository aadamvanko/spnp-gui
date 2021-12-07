package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * Used for the ending visual elements for the arcs.
 */
public interface ArcEnding {

    /**
     * @return JavaFX shape class representing the visual element
     */
    Shape getShape();

    /**
     * Allows the update of the ending in case of changes of the arc (mouse interaction, ...).
     *
     * @param line line on which the ending lies
     */
    void update(Line line);
}
