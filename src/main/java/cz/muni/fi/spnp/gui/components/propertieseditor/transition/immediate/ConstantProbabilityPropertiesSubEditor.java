package cz.muni.fi.spnp.gui.components.propertieseditor.transition.immediate;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.ConstantTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.TransitionProbabilityViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Subeditor for the constant probability.
 */
public class ConstantProbabilityPropertiesSubEditor extends TransitionProbabilitySubEditor {

    private Label valueLabel;
    private TextField valueTextField;

    public ConstantProbabilityPropertiesSubEditor() {
        createView();
    }

    private void createView() {
        valueLabel = new Label("Value:");
        valueTextField = new TextField();
        addRow(valueLabel, valueTextField);
    }

    @Override
    public void bindViewModel(TransitionProbabilityViewModel transitionProbabilityViewModel) {
        super.bindViewModel(transitionProbabilityViewModel);

        var constantTransitionProbabilityViewModel = (ConstantTransitionProbabilityViewModel) transitionProbabilityViewModel;
        valueTextField.textProperty().bindBidirectional(constantTransitionProbabilityViewModel.valueProperty());
    }

    @Override
    public void unbindViewModel() {
        var constantTransitionProbabilityViewModel = (ConstantTransitionProbabilityViewModel) viewModel;
        valueTextField.textProperty().unbindBidirectional(constantTransitionProbabilityViewModel.valueProperty());

        super.unbindViewModel();
    }

}
