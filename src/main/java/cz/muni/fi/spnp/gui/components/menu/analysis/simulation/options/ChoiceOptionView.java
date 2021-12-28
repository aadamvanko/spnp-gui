package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 * View for choice option with the predefined range of values.
 */
public class ChoiceOptionView extends OptionView {

    private final ChoiceBox<ConstantValue> choiceBoxValue;
    private ConstantValueOptionViewModel optionViewModel;

    public ChoiceOptionView(String name, ObservableList<ConstantValue> allowedValues) {
        super(name);

        choiceBoxValue = new ChoiceBox<>(allowedValues);
    }

    public void bind(ConstantValueOptionViewModel optionViewModel) {
        this.optionViewModel = optionViewModel;

        choiceBoxValue.valueProperty().bindBidirectional(optionViewModel.valueProperty());
        checkBoxUse.selectedProperty().bindBidirectional(optionViewModel.useProperty());
        choiceBoxValue.disableProperty().bind(optionViewModel.useProperty().not());
    }

    public void unbind() {
        choiceBoxValue.valueProperty().unbindBidirectional(optionViewModel.valueProperty());
        checkBoxUse.selectedProperty().unbindBidirectional(optionViewModel.useProperty());
        choiceBoxValue.disableProperty().unbind();
    }

    public ChoiceBox<ConstantValue> getChoiceBoxValue() {
        return choiceBoxValue;
    }

}
