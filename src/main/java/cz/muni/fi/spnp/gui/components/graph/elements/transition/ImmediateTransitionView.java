package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import javafx.scene.paint.Color;

public class ImmediateTransitionView extends TransitionView {

    public ImmediateTransitionView(ImmediateTransitionViewModel immediateTransitionViewModel) {
        super(immediateTransitionViewModel);
        createView();
    }

    private void createView() {
        rectangle.setHeight(30);
        rectangle.setWidth(9);
        rectangle.setFill(Color.BLACK);
        rectangle.setSmooth(true);
    }
}
