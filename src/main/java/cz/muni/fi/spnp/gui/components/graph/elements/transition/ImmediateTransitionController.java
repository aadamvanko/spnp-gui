package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.viewmodel.ImmediateTransitionViewModel;
import javafx.scene.paint.Color;

public class ImmediateTransitionController extends TransitionController {

    public ImmediateTransitionController() {
        super();
        createView();
    }

    private void createView() {
        rectangle.setHeight(30);
        rectangle.setWidth(9);
        rectangle.setFill(Color.BLACK);
        rectangle.setSmooth(true);
    }
}
