package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import javafx.scene.input.MouseEvent;

/**
 * Base class for all mouse operations.
 */
public abstract class MouseOperation {

    protected GraphView graphView;

    public MouseOperation(GraphView graphView) {
        this.graphView = graphView;
    }

    public abstract void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent);

    public abstract void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent);

    public abstract void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent);

    /**
     * Called before the "destruction" of the instance.
     */
    public void finish() {
    }
}
