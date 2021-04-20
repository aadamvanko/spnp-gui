package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.scene.control.Label;

public class PositionableElementPropertiesEditor extends PropertiesEditor {

    protected final Label positionXLabel;
    protected final IntegerTextField positionXTextField;
    protected final Label positionYLabel;
    protected final IntegerTextField positionYTextField;

    public PositionableElementPropertiesEditor() {
        positionXLabel = new Label("Position X");
        positionXTextField = new IntegerTextField();
        positionYLabel = new Label("Position Y");
        positionYTextField = new IntegerTextField();

        gridPane.add(positionXLabel, 0, 1);
        gridPane.add(positionXTextField.getTextField(), 1, 1);
        gridPane.add(positionYLabel, 0, 2);
        gridPane.add(positionYTextField.getTextField(), 1, 2);
    }

    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

    }

    public void unbindViewModel() {
        nameTextField.textProperty().unbindBidirectional(viewModel.nameProperty());
        this.viewModel = null;
    }
}
