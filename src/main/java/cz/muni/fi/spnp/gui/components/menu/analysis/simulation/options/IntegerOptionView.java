package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.propertieseditor.common.IntegerTextField;
import javafx.util.converter.IntegerStringConverter;

/**
 * View for integer value option.
 */
public class IntegerOptionView extends OptionView {

    private IntegerTextField integerTextFieldValue;
    private IntegerOptionViewModel optionViewModel;

    public IntegerOptionView(String name) {
        super(name);

        createView();
    }

    private void createView() {
        integerTextFieldValue = new IntegerTextField();
    }

    public void bind(IntegerOptionViewModel optionViewModel) {
        this.optionViewModel = optionViewModel;

        integerTextFieldValue.getTextField().textProperty().bindBidirectional(optionViewModel.valueProperty().asObject(), new IntegerStringConverter());
        checkBoxUse.selectedProperty().bindBidirectional(optionViewModel.useProperty());
        integerTextFieldValue.getTextField().disableProperty().bind(optionViewModel.useProperty().not());
    }

    public void unbind() {
        integerTextFieldValue.getTextField().textProperty().unbindBidirectional(optionViewModel.valueProperty().asObject());
        checkBoxUse.selectedProperty().unbindBidirectional(optionViewModel.useProperty());
        integerTextFieldValue.getTextField().disableProperty().unbind();
    }

    public IntegerTextField getIntegerTextFieldValue() {
        return integerTextFieldValue;
    }

}
