package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class ConnectablePropertiesEditor extends DisplayablePropertiesEditor {

    protected final Label positionXLabel;
    protected final IntegerTextField positionXTextField;
    protected final Label positionYLabel;
    protected final IntegerTextField positionYTextField;

    public ConnectablePropertiesEditor() {
        positionXLabel = new Label("Position X:");
        positionXTextField = new IntegerTextField();
        positionYLabel = new Label("Position Y:");
        positionYTextField = new IntegerTextField();

        addRow(positionXLabel, positionXTextField.getTextField());
        addRow(positionYLabel, positionYTextField.getTextField());
    }

    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        var connectableViewModel = (ConnectableViewModel) viewModel;
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(positionXTextField.getTextField().textProperty(), connectableViewModel.positionXProperty(), converter);
        Bindings.bindBidirectional(positionYTextField.getTextField().textProperty(), connectableViewModel.positionYProperty(), converter);
    }

    public void unbindViewModel() {
        var connectableViewModel = (ConnectableViewModel) viewModel;
        Bindings.unbindBidirectional(positionXTextField.getTextField().textProperty(), connectableViewModel.positionXProperty());
        Bindings.unbindBidirectional(positionYTextField.getTextField().textProperty(), connectableViewModel.positionYProperty());

        // TODO adjust canvas size, prevent too small values

        super.unbindViewModel();
    }
}
