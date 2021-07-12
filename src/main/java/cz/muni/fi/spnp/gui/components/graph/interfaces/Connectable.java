package cz.muni.fi.spnp.gui.components.graph.interfaces;

import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import javafx.geometry.Point2D;

public interface Connectable {

    void addArc(ArcView arcView);

    void removeArc(ArcView arcView);

    Point2D getBorderConnectionPoint(Point2D from);

    boolean containsPoint(Point2D screenPoint);
}
