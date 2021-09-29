package cz.muni.fi.spnp.gui.components.graph.interfaces;

import javafx.geometry.Point2D;

public interface Movable {

    void snapToGrid();

    void snapToPreservedPosition();

    void move(Point2D offset);

    boolean canMove(Point2D offset);

}
