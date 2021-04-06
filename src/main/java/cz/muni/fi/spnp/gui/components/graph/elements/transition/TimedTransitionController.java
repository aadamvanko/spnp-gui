package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.viewmodel.TimedTransitionViewModel;
import javafx.scene.paint.Color;

public class TimedTransitionController extends TransitionController {

    public TimedTransitionController(double x, double y) {
        super(x, y);
        createView();
        createBindings();
    }

    private void createView() {
        rectangle.setHeight(30);
        rectangle.setWidth(15);
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        rectangle.setSmooth(true);
    }

    private void createBindings() {
        TimedTransitionViewModel timedTransitionViewModel = new TimedTransitionViewModel();
        bindViewModel(timedTransitionViewModel);
    }

}
