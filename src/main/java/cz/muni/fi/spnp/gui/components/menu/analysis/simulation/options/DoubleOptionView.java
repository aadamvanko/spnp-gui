package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.propertieseditor.common.DoubleTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MyDoubleStringConverter;

/**
 * View for double value option.
 */
public class DoubleOptionView extends OptionView {

    private DoubleTextField doubleTextFieldValue;
    private DoubleOptionViewModel optionViewModel;

    public DoubleOptionView(String name) {
        super(name);

        createView();
    }

    private void createView() {
        doubleTextFieldValue = new DoubleTextField();
    }

    public void bind(DoubleOptionViewModel optionViewModel) {
        this.optionViewModel = optionViewModel;

        doubleTextFieldValue.getTextField().textProperty().bindBidirectional(optionViewModel.valueProperty().asObject(), new MyDoubleStringConverter());
        checkBoxUse.selectedProperty().bindBidirectional(optionViewModel.useProperty());
        doubleTextFieldValue.getTextField().disableProperty().bind(optionViewModel.useProperty().not());
    }

    public void unbind() {
        doubleTextFieldValue.getTextField().textProperty().unbindBidirectional(optionViewModel.valueProperty().asObject());
        checkBoxUse.selectedProperty().unbindBidirectional(optionViewModel.useProperty());
        doubleTextFieldValue.getTextField().disableProperty().unbind();
    }

    public DoubleTextField getDoubleTextFieldValue() {
        return doubleTextFieldValue;
    }

}
