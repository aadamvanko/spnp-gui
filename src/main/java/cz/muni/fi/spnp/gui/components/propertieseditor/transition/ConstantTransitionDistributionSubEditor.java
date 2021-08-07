package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConstantTransitionDistributionSubEditor extends TransitionDistributionSubEditor {

    private List<Label> valuesLabels;
    private List<TextField> valuesTextFields;

    public ConstantTransitionDistributionSubEditor() {
        createView();
    }

    private void createView() {
        valuesLabels = new ArrayList<>();
        valuesTextFields = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            var valueLabel = new Label("Value:");
            valuesLabels.add(valueLabel);
            var valueTextField = new TextField();
            valuesTextFields.add(valueTextField);
            addRow(valueLabel, valueTextField);
        }
    }

    @Override
    public List<PropertiesEditorRow> getRows() {
        return super.getRows().stream()
                .limit(viewModel.getValues().size())
                .collect(Collectors.toList());
    }

    @Override
    public void bindViewModel(TransitionDistributionViewModel transitionDistributionViewModel) {
        super.bindViewModel(transitionDistributionViewModel);

        for (int i = 0; i < transitionDistributionViewModel.getValues().size(); i++) {
            valuesLabels.get(i).setText(String.format("%s:", transitionDistributionViewModel.getValuesNames().get(i)));
            valuesTextFields.get(i).textProperty().bindBidirectional(transitionDistributionViewModel.getValues().get(i));
        }
    }

    @Override
    public void unbindViewModel() {
        for (int i = 0; i < viewModel.getValues().size(); i++) {
            valuesTextFields.get(i).textProperty().unbindBidirectional(viewModel.getValues().get(i));
        }

        super.unbindViewModel();
    }

}
