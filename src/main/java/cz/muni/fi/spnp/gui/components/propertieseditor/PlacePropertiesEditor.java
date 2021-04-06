package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.Property;
import javafx.scene.control.Label;

public class PlacePropertiesEditor extends PropertiesEditor {

    private final Label numberOfTokensLabel;
    private final IntegerTextField numberOfTokensTextField;

    public PlacePropertiesEditor() {

        numberOfTokensLabel = new Label("Tokens:");
        numberOfTokensTextField = new IntegerTextField();

        gridPane.add(numberOfTokensLabel, 0, 1);
        gridPane.add(numberOfTokensTextField.getTextField(), 1, 1);
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        PlaceViewModel placeViewModel = (PlaceViewModel) viewModel;
        numberOfTokensTextField.getTextFormatter().valueProperty().bindBidirectional((Property) placeViewModel.numberOfTokensProperty());
    }

    @Override
    public void unbindViewModel() {
        PlaceViewModel placeViewModel = (PlaceViewModel) viewModel;
        numberOfTokensTextField.getTextFormatter().valueProperty().unbindBidirectional((Property) placeViewModel.numberOfTokensProperty());
        super.unbindViewModel();
    }

}
