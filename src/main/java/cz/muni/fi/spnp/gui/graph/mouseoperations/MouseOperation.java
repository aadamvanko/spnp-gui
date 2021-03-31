package cz.muni.fi.spnp.gui.graph.mouseoperations;

import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.elements.GraphElement;
import javafx.scene.input.MouseEvent;

public abstract class MouseOperation {

    protected GraphView graphView;

    public MouseOperation(GraphView graphView) {
        this.graphView = graphView;
    }

    public abstract void mousePressedHandler(GraphElement graphElement, MouseEvent mouseEvent);

    public abstract void mouseDraggedHandler(GraphElement graphElement, MouseEvent mouseEvent);

    public abstract void mouseReleasedHandler(GraphElement graphElement, MouseEvent mouseEvent);

    public void finish() {
    }
}
