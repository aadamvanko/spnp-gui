package cz.muni.fi.spnp.gui.components.diagram.graph.interfaces;

import javafx.geometry.Point2D;

/**
 * Represents visual elements that can be moved.
 */
public interface Movable {

    /**
     * Snaps the element to the grid.
     */
    void snapToGrid();

    /**
     * Moves the element by specified offset vector.
     *
     * @param offset vector to add to the actual position
     */
    void move(Point2D offset);

    /**
     * Checks whether the position obtained by adding the offset vector is valid.
     *
     * @param offset vector to be added to the actual position
     * @return true if the new position would be valid, false otherwise
     */
    boolean canMove(Point2D offset);

}
