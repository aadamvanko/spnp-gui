package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ImmediateTransitionPropertiesEditor extends TransitionPropertiesEditor {

    private final Map<TransitionProbabilityType, TransitionProbabilitySubEditor> subEditors;
    private Label transitionProbabilityTypeLabel;
    private ChoiceBox<TransitionProbabilityType> transitionProbabilityTypeChoiceBox;

    public ImmediateTransitionPropertiesEditor() {
        createView();

        subEditors = new HashMap<>();
        subEditors.put(TransitionProbabilityType.Constant, new ConstantProbabilityPropertiesSubEditor());
        subEditors.put(TransitionProbabilityType.Functional, new FunctionalProbabilityPropertiesSubEditor());
        subEditors.put(TransitionProbabilityType.PlaceDependent, new PlaceDependentProbabilityPropertiesSubEditor());
    }

    private void createView() {
        transitionProbabilityTypeLabel = new Label("Probability type:");
        transitionProbabilityTypeChoiceBox = new ChoiceBox<>();
        var probabilityTypes = FXCollections.observableArrayList(TransitionProbabilityType.values());
        transitionProbabilityTypeChoiceBox.setItems(probabilityTypes);
        transitionProbabilityTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::onTransitionProbabilityTypeChangedListener);

        addRow(transitionProbabilityTypeLabel, transitionProbabilityTypeChoiceBox);
    }

    private void onTransitionProbabilityTypeChangedListener(ObservableValue<? extends TransitionProbabilityType> observable, TransitionProbabilityType oldType, TransitionProbabilityType newType) {
        if (oldType != null) {
            var oldSubEditor = subEditors.get(oldType);
            oldSubEditor.unbindViewModel();
            oldSubEditor.unbindDiagramViewModel();
            oldSubEditor.getRows().forEach(row -> gridPane.getChildren().removeAll(row.getLeft(), row.getRight()));
        }

        if (newType != null) {
            var immediateTransitionViewModel = (ImmediateTransitionViewModel) viewModel;
            immediateTransitionViewModel.setTransitionProbability(createNewProbabilityViewModel(newType));

            var newSubEditor = subEditors.get(newType);
            newSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));

            newSubEditor.bindDiagramViewModel(diagramViewModel);
            newSubEditor.bindViewModel(immediateTransitionViewModel.getTransitionProbability());
        }
    }

    private TransitionProbabilityViewModel createNewProbabilityViewModel(TransitionProbabilityType transitionProbabilityType) {
        switch (transitionProbabilityType) {
            case Constant:
                return new ConstantTransitionProbabilityViewModel();
            case Functional:
                return new FunctionalTransitionProbabilityViewModel();
            case PlaceDependent:
                return new PlaceDependentTransitionProbabilityViewModel();
            default:
                return null;
        }
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        var immediateTransitionViewModel = (ImmediateTransitionViewModel) viewModel;
        transitionProbabilityTypeChoiceBox.getSelectionModel().select(immediateTransitionViewModel.getTransitionProbability().getEnumType());
    }

}
