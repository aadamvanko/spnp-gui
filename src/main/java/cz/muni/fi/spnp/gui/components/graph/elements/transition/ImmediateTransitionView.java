package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import javafx.scene.paint.Color;

public class ImmediateTransitionView extends TransitionView {


    private final static double WIDTH = 9;
    private final static double HEIGHT = 30;

    public ImmediateTransitionView(GraphView graphView, ImmediateTransitionViewModel immediateTransitionViewModel) {
        super(graphView, immediateTransitionViewModel);
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
        rectangle.setFill(Color.BLACK);
        rectangle.setSmooth(true);
    }
}
