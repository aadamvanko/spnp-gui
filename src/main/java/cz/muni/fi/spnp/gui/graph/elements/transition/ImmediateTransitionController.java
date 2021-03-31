package cz.muni.fi.spnp.gui.graph.elements.transition;

import javafx.scene.paint.Color;

public class ImmediateTransitionController extends TransitionController {

    public ImmediateTransitionController(double x, double y) {
        super(x, y);

        rectangle.setHeight(30);
        rectangle.setWidth(9);
        rectangle.setFill(Color.BLACK);
        rectangle.setSmooth(true);
    }
}
