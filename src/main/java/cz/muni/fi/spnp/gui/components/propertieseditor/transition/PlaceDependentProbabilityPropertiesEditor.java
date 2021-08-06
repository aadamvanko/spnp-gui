package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.components.propertieseditor.DoubleTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.PlaceViewModelStringConverter;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.PlaceDependentTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.TransitionProbabilityViewModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class PlaceDependentProbabilityPropertiesEditor extends TransitionProbabilitySubEditor {

    private final ListChangeListener<? super ElementViewModel> onPlacesChangedListener;
    private Label valueLabel;
    private DoubleTextField valueTextField;
    private Label placeLabel;
    private ChoiceBox<PlaceViewModel> dependentPlaceChoiceBox;

    public PlaceDependentProbabilityPropertiesEditor() {
        createView();

        this.onPlacesChangedListener = this::onPlacesChangedListener;
    }

    private void createView() {
        valueLabel = new Label("Value:");
        valueTextField = new DoubleTextField();
        addRow(valueLabel, valueTextField.getTextField());

        placeLabel = new Label("Place:");
        dependentPlaceChoiceBox = new ChoiceBox<>();
        addRow(placeLabel, dependentPlaceChoiceBox);
    }

    private void onPlacesChangedListener(ListChangeListener.Change<? extends ElementViewModel> placesChange) {
        var placesCopy = FXCollections.observableArrayList(diagramViewModel.getPlaces());
        placesCopy.add(0, null);
        dependentPlaceChoiceBox.setItems(placesCopy);
    }

    @Override
    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        super.bindDiagramViewModel(diagramViewModel);

        dependentPlaceChoiceBox.setConverter(new PlaceViewModelStringConverter(diagramViewModel));

        diagramViewModel.getElements().addListener(this.onPlacesChangedListener);
        onPlacesChangedListener(null);
    }

    @Override
    public void unbindDiagramViewModel() {
        diagramViewModel.getElements().removeListener(this.onPlacesChangedListener);

        super.unbindDiagramViewModel();
    }

    @Override
    public void bindViewModel(TransitionProbabilityViewModel transitionProbabilityViewModel) {
        super.bindViewModel(transitionProbabilityViewModel);

        var placeDependentTransitionProbabilityViewModel = (PlaceDependentTransitionProbabilityViewModel) transitionProbabilityViewModel;
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(valueTextField.getTextField().textProperty(), placeDependentTransitionProbabilityViewModel.valueProperty(), converter);

        dependentPlaceChoiceBox.valueProperty().bindBidirectional(placeDependentTransitionProbabilityViewModel.dependentPlaceProperty());
        dependentPlaceChoiceBox.getSelectionModel().select(placeDependentTransitionProbabilityViewModel.getDependentPlace());
    }

    public void unbindViewModel() {
        var placeDependentTransitionProbabilityViewModel = (PlaceDependentTransitionProbabilityViewModel) viewModel;

        Bindings.unbindBidirectional(valueTextField.getTextField().textProperty(), placeDependentTransitionProbabilityViewModel.valueProperty());
        dependentPlaceChoiceBox.valueProperty().unbindBidirectional(placeDependentTransitionProbabilityViewModel.dependentPlaceProperty());

        super.unbindViewModel();
    }

}
