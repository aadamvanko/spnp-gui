package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public interface ArcEnding {

    Shape getShape();

    void update(Line line);
}
