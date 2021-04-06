package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.viewmodel.ImmediateTransitionViewModel;
import javafx.scene.paint.Color;

public class ImmediateTransitionController extends TransitionController {

    public ImmediateTransitionController(double x, double y) {
        super(x, y);
        createView();
        createBindings();
    }

    private void createView() {
        rectangle.setHeight(30);
        rectangle.setWidth(9);
        rectangle.setFill(Color.BLACK);
        rectangle.setSmooth(true);
    }

    private void createBindings() {
        ImmediateTransitionViewModel immediateTransitionViewModel = new ImmediateTransitionViewModel();
        bindViewModel(immediateTransitionViewModel);
    }
}
