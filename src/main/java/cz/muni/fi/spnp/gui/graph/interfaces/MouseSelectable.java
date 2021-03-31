package cz.muni.fi.spnp.gui.graph.interfaces;

import javafx.geometry.Point2D;

public interface MouseSelectable {
    Point2D getShapeCenter();

    Point2D rightBottomCorner();
}
