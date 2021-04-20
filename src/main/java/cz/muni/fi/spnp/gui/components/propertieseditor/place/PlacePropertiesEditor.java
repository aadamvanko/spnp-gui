package cz.muni.fi.spnp.gui.components.propertieseditor.place;

import cz.muni.fi.spnp.gui.components.propertieseditor.IntegerTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.PositionableElementPropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.property.Property;
import javafx.scene.control.Label;

public class PlacePropertiesEditor extends PositionableElementPropertiesEditor {

    private final Label numberOfTokensLabel;
    private final IntegerTextField numberOfTokensTextField;

    public PlacePropertiesEditor() {

        numberOfTokensLabel = new Label("Tokens:");
        numberOfTokensTextField = new IntegerTextField();

        gridPane.add(numberOfTokensLabel, 0, 3);
        gridPane.add(numberOfTokensTextField.getTextField(), 1, 3);
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
