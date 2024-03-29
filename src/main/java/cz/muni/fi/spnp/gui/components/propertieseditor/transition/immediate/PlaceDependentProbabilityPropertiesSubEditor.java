package cz.muni.fi.spnp.gui.components.propertieseditor.transition.immediate;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.PlaceDependentTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.TransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MyChoiceBox;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.PlaceViewModelStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Subeditor for the place dependent probability.
 */
public class PlaceDependentProbabilityPropertiesSubEditor extends TransitionProbabilitySubEditor {

    private final ListChangeListener<? super ElementViewModel> onPlacesChangedListener;
    private Label valueLabel;
    private TextField valueTextField;
    private Label dependentPlaceLabel;
    private MyChoiceBox<PlaceViewModel> dependentPlaceChoiceBox;

    public PlaceDependentProbabilityPropertiesSubEditor() {
        createView();

        this.onPlacesChangedListener = this::onPlacesChangedListener;
    }

    private void createView() {
        valueLabel = new Label("Value:");
        valueTextField = new TextField();
        addRow(valueLabel, valueTextField);

        dependentPlaceLabel = new Label("Dependent place:");
        dependentPlaceChoiceBox = new MyChoiceBox<>();
        addRow(dependentPlaceLabel, dependentPlaceChoiceBox);
    }

    private void onPlacesChangedListener(ListChangeListener.Change<? extends ElementViewModel> placesChange) {
        var placesCopy = FXCollections.observableArrayList(diagramViewModel.getPlaces());
        placesCopy.add(0, null);
        dependentPlaceChoiceBox.setItemsWithSelected(placesCopy);
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
        valueTextField.textProperty().bindBidirectional(placeDependentTransitionProbabilityViewModel.valueProperty());
        dependentPlaceChoiceBox.valueProperty().bindBidirectional(placeDependentTransitionProbabilityViewModel.dependentPlaceProperty());
    }

    public void unbindViewModel() {
        var placeDependentTransitionProbabilityViewModel = (PlaceDependentTransitionProbabilityViewModel) viewModel;

        valueTextField.textProperty().unbindBidirectional(placeDependentTransitionProbabilityViewModel.valueProperty());
        dependentPlaceChoiceBox.valueProperty().unbindBidirectional(placeDependentTransitionProbabilityViewModel.dependentPlaceProperty());

        super.unbindViewModel();
    }

}
