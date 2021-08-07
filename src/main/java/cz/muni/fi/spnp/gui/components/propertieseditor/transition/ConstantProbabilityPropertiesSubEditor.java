package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.components.propertieseditor.DoubleTextField;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ConstantTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.TransitionProbabilityViewModel;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

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
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(valueTextField.getTextField().textProperty(), constantTransitionProbabilityViewModel.valueProperty(), converter);
    }

    @Override
    public void unbindViewModel() {
        var constantTransitionProbabilityViewModel = (ConstantTransitionProbabilityViewModel) viewModel;
        Bindings.unbindBidirectional(valueTextField.getTextField().textProperty(), constantTransitionProbabilityViewModel.valueProperty());

        super.unbindViewModel();
    }

}
