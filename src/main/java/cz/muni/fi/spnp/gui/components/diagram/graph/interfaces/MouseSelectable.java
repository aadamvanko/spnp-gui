package cz.muni.fi.spnp.gui.components.diagram.graph.interfaces;

import javafx.geometry.Point2D;

public interface MouseSelectable {
    Point2D getShapeCenter();

    Point2D rightBottomCorner();
}
