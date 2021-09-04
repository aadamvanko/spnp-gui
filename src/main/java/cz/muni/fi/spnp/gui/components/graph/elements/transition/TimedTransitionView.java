package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import javafx.scene.paint.Color;

public class TimedTransitionView extends TransitionView {

    private final static double WIDTH = 15;
    private final static double HEIGHT = 30;

    public TimedTransitionView(GraphView graphView, TimedTransitionViewModel timedTransitionViewModel) {
        super(graphView, timedTransitionViewModel);

        createView();
    }

    @Override
    protected double getRectangleDefaultWidth() {
        return WIDTH;
    }

    @Override
    protected double getRectangleDefaultHeight() {
        return HEIGHT;
    }

    private void createView() {
        rectangle.setStrokeWidth(1);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        rectangle.setSmooth(true);
    }
}
