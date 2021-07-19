package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import javafx.scene.paint.Color;

public class TimedTransitionView extends TransitionView {

    public TimedTransitionView(GraphView graphView, TimedTransitionViewModel timedTransitionViewModel) {
        super(graphView, timedTransitionViewModel);

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
