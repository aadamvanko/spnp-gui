package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public abstract class ArcEnding {

    public abstract Shape getShape();

    public abstract void update(Line line);
}
