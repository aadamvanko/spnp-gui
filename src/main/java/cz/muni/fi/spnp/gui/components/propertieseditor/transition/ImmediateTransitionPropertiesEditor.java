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
    private TransitionProbabilitySubEditor selectedSubEditor;

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
        TransitionProbabilityViewModel oldTransitionProbability = null;
        if (selectedSubEditor != null) {
            oldTransitionProbability = selectedSubEditor.getViewModel();
        }

        unbindSelectedSubEditor();
        if (oldType != null && oldType != newType && oldTransitionProbability != null && oldTransitionProbability == getViewModel().getTransitionProbability()) {
            getViewModel().setTransitionProbability(createNewProbabilityViewModel(newType));
        }
        bindSelectedSubEditor(newType);
    }

    private TransitionProbabilityViewModel createNewProbabilityViewModel(TransitionProbabilityType transitionProbabilityType) {
        switch (transitionProbabilityType) {
            case Constant:
                return new ConstantTransitionProbabilityViewModel();
            case Functional:
                return new FunctionalTransitionProbabilityViewModel();
            case PlaceDependent:
                return new PlaceDependentTransitionProbabilityViewModel();
        }
        throw new AssertionError("Unknown transition probability type " + transitionProbabilityType);
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        transitionProbabilityTypeChoiceBox.getSelectionModel().select(getViewModel().getTransitionProbability().getEnumType());
        bindSelectedSubEditor(getViewModel().getTransitionProbability().getEnumType());
    }

    @Override
    public void unbindViewModel() {
        unbindSelectedSubEditor();

        super.unbindViewModel();
    }

    @Override
    protected ImmediateTransitionViewModel getViewModel() {
        return (ImmediateTransitionViewModel) viewModel;
    }

    private void bindSelectedSubEditor(TransitionProbabilityType transitionProbabilityType) {
        if (selectedSubEditor != null) {
            return;
        }

        selectedSubEditor = subEditors.get(transitionProbabilityType);
        selectedSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));
        selectedSubEditor.bindDiagramViewModel(diagramViewModel);
        selectedSubEditor.bindViewModel(getViewModel().getTransitionProbability());
    }

    private void unbindSelectedSubEditor() {
        if (selectedSubEditor != null) {
            selectedSubEditor.unbindViewModel();
            selectedSubEditor.unbindDiagramViewModel();
            selectedSubEditor.getRows().forEach(row -> gridPane.getChildren().removeAll(row.getLeft(), row.getRight()));
        }
        selectedSubEditor = null;
    }

}
