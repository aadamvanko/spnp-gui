package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.TransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.PlaceViewModelStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlaceDependentTransitionDistributionSubEditor extends ConstantTransitionDistributionSubEditor {

    private final ListChangeListener<? super ElementViewModel> onPlacesChangedListener;
    private Label dependentPlaceLabel;
    private ChoiceBox<PlaceViewModel> dependentPlaceChoiceBox;
    private PropertiesEditorRow dependentPlaceRow;

    public PlaceDependentTransitionDistributionSubEditor() {
        createView();

        this.onPlacesChangedListener = this::onPlacesChangedListener;
    }

    private void createView() {
        dependentPlaceLabel = new Label("Dependent place:");
        dependentPlaceChoiceBox = new ChoiceBox<>();
        dependentPlaceRow = new PropertiesEditorRow(dependentPlaceLabel, dependentPlaceChoiceBox);
    }

    private void onPlacesChangedListener(ListChangeListener.Change<? extends ElementViewModel> placesChange) {
        var placesCopy = FXCollections.observableArrayList(diagramViewModel.getPlaces());
        placesCopy.add(0, null);
        dependentPlaceChoiceBox.setItems(placesCopy);
    }

    @Override
    public List<PropertiesEditorRow> getRows() {
        return Stream.concat(super.getRows().stream(), Stream.of(dependentPlaceRow))
                .collect(Collectors.toList());
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
    public void bindViewModel(TransitionDistributionViewModel transitionDistributionViewModel) {
        super.bindViewModel(transitionDistributionViewModel);

        dependentPlaceChoiceBox.valueProperty().bindBidirectional(transitionDistributionViewModel.dependentPlaceProperty());
    }

    @Override
    public void unbindViewModel() {
        dependentPlaceChoiceBox.valueProperty().unbindBidirectional(viewModel.dependentPlaceProperty());

        super.unbindViewModel();
    }

}
