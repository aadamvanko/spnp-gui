package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.scene.control.Label;
import javafx.util.converter.DoubleStringConverter;

public abstract class ConnectablePropertiesEditor extends ElementPropertiesEditor {

    protected final Label positionXLabel;
    protected final DoubleTextField positionXTextField;
    protected final Label positionYLabel;
    protected final DoubleTextField positionYTextField;

    public ConnectablePropertiesEditor() {
        positionXLabel = new Label("Position X:");
        positionXTextField = new DoubleTextField();
        positionYLabel = new Label("Position Y:");
        positionYTextField = new DoubleTextField();

        addRow(positionXLabel, positionXTextField.getTextField());
        addRow(positionYLabel, positionYTextField.getTextField());

    }

    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        positionXTextField.getTextField().textProperty().bindBidirectional(getViewModel().positionXProperty().asObject(), new DoubleStringConverter());
        positionYTextField.getTextField().textProperty().bindBidirectional(getViewModel().positionYProperty().asObject(), new DoubleStringConverter());
    }

    public void unbindViewModel() {
        positionXTextField.getTextField().textProperty().unbindBidirectional(getViewModel().positionXProperty().asObject());
        positionYTextField.getTextField().textProperty().unbindBidirectional(getViewModel().positionYProperty().asObject());

        // TODO adjust canvas size, prevent too small values

        super.unbindViewModel();
    }

    @Override
    protected ConnectableViewModel getViewModel() {
        return (ConnectableViewModel) viewModel;
    }

}
