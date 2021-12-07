package cz.muni.fi.spnp.gui.components.diagram.graph.interfaces;

import javafx.geometry.Point2D;

/**
 * Implemented by the visual elements that can be selected via mouse.
 */
public interface MouseSelectable {

    /**
     * @return center of the visual element
     */
    Point2D getShapeCenter();

    /**
     * @return bottom right corner of the visual element
     */
    Point2D rightBottomCorner();

}
