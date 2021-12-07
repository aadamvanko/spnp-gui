package cz.muni.fi.spnp.gui.components.diagram.graph.interfaces;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcView;
import javafx.geometry.Point2D;

/**
 * Represents visual element, which can be connected another one via arc.
 */
public interface Connectable {

    /**
     * Adds connected arc.
     *
     * @param arcView connected arc
     */
    void addArc(ArcView arcView);

    /**
     * Removes disconnected arc.
     *
     * @param arcView disconnected arc
     */
    void removeArc(ArcView arcView);

    /**
     * Calculates the connection position on the border of the shape's outline.
     *
     * @param from origin of the line connected to the center of this element
     * @return closest position to the from position on the element's border
     */
    Point2D getBorderConnectionPoint(Point2D from);

    /**
     * Checks whether the given point on the screen is inside the element.
     *
     * @param screenPoint position in screen space coordinates
     * @return true if the point is inside the element, false otherwise
     */
    boolean containsPoint(Point2D screenPoint);

}
