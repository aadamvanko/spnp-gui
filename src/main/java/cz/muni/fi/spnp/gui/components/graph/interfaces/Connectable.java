package cz.muni.fi.spnp.gui.components.graph.interfaces;

import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcController;
import javafx.geometry.Point2D;

public interface Connectable {

    void addArc(ArcController arcController);

    void removeArc(ArcController arcController);

    Point2D getBorderConnectionPoint(Point2D from);

    boolean containsPoint(Point2D screenPoint);
}
