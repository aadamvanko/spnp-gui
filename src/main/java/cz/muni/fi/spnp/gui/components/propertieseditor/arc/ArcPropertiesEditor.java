package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.gui.components.propertieseditor.ElementPropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ArcMultiplicityType;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class ArcPropertiesEditor extends ElementPropertiesEditor {

    private final Map<ArcMultiplicityType, ArcMultiplicitySubEditor> subEditors;
    private Label arcMultiplicityTypeLabel;
    private ChoiceBox<ArcMultiplicityType> arcMultiplicityTypeChoiceBox;

    public ArcPropertiesEditor() {
        createView();

        subEditors = new HashMap<>();
        subEditors.put(ArcMultiplicityType.Constant, new ConstantMultiplicitySubEditor());
        subEditors.put(ArcMultiplicityType.Function, new FunctionalMultiplicitySubEditor());
    }

    private void createView() {
        arcMultiplicityTypeLabel = new Label("Multiplicity type:");
        var arcMultiplicityTypes = FXCollections.observableArrayList(ArcMultiplicityType.values());
        arcMultiplicityTypeChoiceBox = new ChoiceBox<>(arcMultiplicityTypes);
        addRow(arcMultiplicityTypeLabel, arcMultiplicityTypeChoiceBox);

        arcMultiplicityTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::arcMultiplicityTypeChanged);
    }

    private void arcMultiplicityTypeChanged(ObservableValue<? extends ArcMultiplicityType> observableValue, ArcMultiplicityType oldType, ArcMultiplicityType newType) {
        if (oldType != null) {
            var oldSubEditor = subEditors.get(oldType);
            oldSubEditor.unbindViewModel();
            oldSubEditor.unbindDiagramViewModel();
            oldSubEditor.getRows().forEach(row -> gridPane.getChildren().removeAll(row.getLeft(), row.getRight()));
        }

        if (newType != null) {
            var newSubEditor = subEditors.get(newType);
            newSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));
            newSubEditor.bindDiagramViewModel(diagramViewModel);
            newSubEditor.bindViewModel((ArcViewModel) viewModel);
        }
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        arcMultiplicityTypeChoiceBox.valueProperty().bindBidirectional(getViewModel().multiplicityTypeProperty());
    }

    @Override
    public void unbindViewModel() {
        arcMultiplicityTypeChoiceBox.valueProperty().unbindBidirectional(getViewModel().multiplicityTypeProperty());

        super.unbindViewModel();
    }

    @Override
    protected ArcViewModel getViewModel() {
        return (ArcViewModel) viewModel;
    }

}
