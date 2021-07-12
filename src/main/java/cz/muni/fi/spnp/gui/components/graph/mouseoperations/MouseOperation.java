package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import javafx.scene.input.MouseEvent;

public abstract class MouseOperation {

    protected GraphView graphView;

    public MouseOperation(GraphView graphView) {
        this.graphView = graphView;
    }

    public abstract void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent);

    public abstract void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent);

    public abstract void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent);

    public void finish() {
    }
}
