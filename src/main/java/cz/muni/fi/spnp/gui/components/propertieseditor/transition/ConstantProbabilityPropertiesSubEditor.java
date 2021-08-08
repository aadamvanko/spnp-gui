package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.components.propertieseditor.DoubleTextField;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ConstantTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.TransitionProbabilityViewModel;
import javafx.scene.control.Label;
import javafx.util.converter.DoubleStringConverter;

public class ConstantProbabilityPropertiesSubEditor extends TransitionProbabilitySubEditor {

    private Label valueLabel;
    private DoubleTextField valueTextField;

    public ConstantProbabilityPropertiesSubEditor() {
        createView();
    }

    private void createView() {
        valueLabel = new Label("Value:");
        valueTextField = new DoubleTextField();
        addRow(valueLabel, valueTextField.getTextField());
    }

    @Override
    public void bindViewModel(TransitionProbabilityViewModel transitionProbabilityViewModel) {
        super.bindViewModel(transitionProbabilityViewModel);

        var constantTransitionProbabilityViewModel = (ConstantTransitionProbabilityViewModel) transitionProbabilityViewModel;
        valueTextField.getTextField().textProperty().bindBidirectional(constantTransitionProbabilityViewModel.valueProperty().asObject(), new DoubleStringConverter());
    }

    @Override
    public void unbindViewModel() {
        var constantTransitionProbabilityViewModel = (ConstantTransitionProbabilityViewModel) viewModel;
        valueTextField.getTextField().textProperty().unbindBidirectional(constantTransitionProbabilityViewModel.valueProperty().asObject());

        super.unbindViewModel();
    }

}
