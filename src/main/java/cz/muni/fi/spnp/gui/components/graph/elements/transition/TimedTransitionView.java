package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import javafx.scene.paint.Color;

public class TimedTransitionView extends TransitionView {

    public TimedTransitionView() {
        super();
        createView();
    }

    private void createView() {
        rectangle.setHeight(30);
        rectangle.setWidth(15);
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        rectangle.setSmooth(true);
    }
}
