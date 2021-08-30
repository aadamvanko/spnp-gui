package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.gui.components.propertieseditor.ElementPropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ArcMultiplicityType;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ArcPropertiesEditor extends ElementPropertiesEditor {

    private final Map<ArcMultiplicityType, ArcMultiplicitySubEditor> subEditors;
    private Label arcMultiplicityTypeLabel;
    private ChoiceBox<ArcMultiplicityType> arcMultiplicityTypeChoiceBox;
    private final ChangeListener<Boolean> onIsFlushingChangedListener;
    private final ChangeListener<ArcMultiplicityType> onMultiplicityTypeChangedListener;
    private Label isFlushingLabel;
    private CheckBox isFlushingCheckBox;
    private ArcMultiplicitySubEditor selectedSubEditor;

    public ArcPropertiesEditor() {
        createView();

        onIsFlushingChangedListener = this::onIsFlushingChangedListener;
        onMultiplicityTypeChangedListener = this::onMultiplicityTypeChangedListener;

        subEditors = new HashMap<>();
        subEditors.put(ArcMultiplicityType.Constant, new ConstantMultiplicitySubEditor());
        subEditors.put(ArcMultiplicityType.Function, new FunctionalMultiplicitySubEditor());
    }

    @Override
    protected Class<?> getElementClassForDuplicity() {
        return ArcViewModel.class;
    }

    private void createView() {
        arcMultiplicityTypeLabel = new Label("Multiplicity type:");
        var arcMultiplicityTypes = FXCollections.observableArrayList(ArcMultiplicityType.values());
        arcMultiplicityTypeChoiceBox = new ChoiceBox<>(arcMultiplicityTypes);
        addRow(arcMultiplicityTypeLabel, arcMultiplicityTypeChoiceBox);

        isFlushingLabel = new Label("Flushing:");
        isFlushingCheckBox = new CheckBox();
        addRow(isFlushingLabel, isFlushingCheckBox);
    }

    private void onIsFlushingChangedListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            getViewModel().multiplicityTypeProperty().set(ArcMultiplicityType.Function);
            var flushFunction = getViewModel().getMultiplicityFunction();
            if (oldValue != null && oldValue == false) {
                flushFunction = diagramViewModel.createFlushFunction(getViewModel().getFromViewModel().getName());
            }
            getViewModel().multiplicityFunctionProperty().set(flushFunction);
        } else {
            diagramViewModel.getFunctions().remove(getViewModel().getMultiplicityFunction());
        }
    }

    private void onMultiplicityTypeChangedListener(ObservableValue<? extends ArcMultiplicityType> observableValue, ArcMultiplicityType oldValue, ArcMultiplicityType newValue) {
        if (newValue == ArcMultiplicityType.Constant) {
            getViewModel().isFlushingProperty().set(false);
        }

        unbindSelectedSubEditor();
        bindSelectedSubEditor(newValue);
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        arcMultiplicityTypeChoiceBox.valueProperty().bindBidirectional(getViewModel().multiplicityTypeProperty());
        arcMultiplicityTypeChoiceBox.disableProperty().bind(getViewModel().isFlushingProperty());
        isFlushingCheckBox.selectedProperty().bindBidirectional(getViewModel().isFlushingProperty());

        getViewModel().isFlushingProperty().addListener(this.onIsFlushingChangedListener);
        onIsFlushingChangedListener(null, null, getViewModel().isFlushing());

        getViewModel().multiplicityTypeProperty().addListener(this.onMultiplicityTypeChangedListener);
        onMultiplicityTypeChangedListener(null, null, getViewModel().getMultiplicityType());

        gridPane.getChildren().removeAll(isFlushingLabel, isFlushingCheckBox);
        if (getViewModel().getFromViewModel() instanceof PlaceViewModel) {
            gridPane.addRow(gridPane.getRowCount(), isFlushingLabel, isFlushingCheckBox);
        }

        unbindSelectedSubEditor();
        bindSelectedSubEditor(getViewModel().getMultiplicityType());
    }

    @Override
    public void unbindViewModel() {
        arcMultiplicityTypeChoiceBox.valueProperty().unbindBidirectional(getViewModel().multiplicityTypeProperty());
        arcMultiplicityTypeChoiceBox.disableProperty().unbind();
        isFlushingCheckBox.selectedProperty().unbindBidirectional(getViewModel().isFlushingProperty());

        getViewModel().isFlushingProperty().removeListener(this.onIsFlushingChangedListener);
        getViewModel().multiplicityTypeProperty().removeListener(this.onMultiplicityTypeChangedListener);

        unbindSelectedSubEditor();

        gridPane.getChildren().removeAll(isFlushingLabel, isFlushingCheckBox);

        super.unbindViewModel();
    }

    @Override
    protected ArcViewModel getViewModel() {
        return (ArcViewModel) viewModel;
    }

    private void bindSelectedSubEditor(ArcMultiplicityType arcMultiplicityType) {
        if (selectedSubEditor != null) {
            return;
        }

        selectedSubEditor = subEditors.get(arcMultiplicityType);
        selectedSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));
        selectedSubEditor.bindDiagramViewModel(diagramViewModel);
        selectedSubEditor.bindViewModel(getViewModel());
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
