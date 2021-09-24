package cz.muni.fi.spnp.gui.components.propertieseditor.place;

import cz.muni.fi.spnp.gui.components.propertieseditor.ElementPropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PlacePropertiesEditor extends ElementPropertiesEditor {

    private final Label numberOfTokensLabel;
    private final TextField numberOfTokensTextField;

    public PlacePropertiesEditor() {

        numberOfTokensLabel = new Label("Tokens:");
        numberOfTokensTextField = new TextField();
        addRow(numberOfTokensLabel, numberOfTokensTextField);
    }

    @Override
    protected Class<?> getElementClassForDuplicity() {
        return PlaceViewModel.class;
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        PlaceViewModel placeViewModel = (PlaceViewModel) viewModel;
        numberOfTokensTextField.textProperty().bindBidirectional(placeViewModel.numberOfTokensProperty());
    }

    @Override
    public void unbindViewModel() {
        PlaceViewModel placeViewModel = (PlaceViewModel) viewModel;
        numberOfTokensTextField.textProperty().unbindBidirectional(placeViewModel.numberOfTokensProperty());
        super.unbindViewModel();
    }

}
