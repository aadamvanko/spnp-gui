package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.gui.components.propertieseditor.DisplayablePropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ArcMultiplicityType;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ArcPropertiesEditor extends DisplayablePropertiesEditor {

    private final Label arcMultiplicityTypeLabel;
    private final ChoiceBox<ArcMultiplicityType> arcMultiplicityTypeChoiceBox;
    private final Label multiplicityLabel;
    private final TextField multiplicityTextField;
    private final Label multiplicityFunctionLabel;
    private final ChoiceBox<String> multiplicityFunctionChoiceBox;

    public ArcPropertiesEditor() {
        arcMultiplicityTypeLabel = new Label("Multiplicity type:");
        var arcMultiplicityTypes = FXCollections.observableArrayList(ArcMultiplicityType.CONSTANT, ArcMultiplicityType.FUNCTION);
        arcMultiplicityTypeChoiceBox = new ChoiceBox<>(arcMultiplicityTypes);
        multiplicityLabel = new Label("Multiplicity:");
        multiplicityTextField = new TextField();
        multiplicityFunctionLabel = new Label("Multiplicity function:");
        multiplicityFunctionChoiceBox = new ChoiceBox<>();

        arcMultiplicityTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::arcMultiplicityTypeChanged);

        gridPane.add(arcMultiplicityTypeLabel, 0, 1);
        gridPane.add(arcMultiplicityTypeChoiceBox, 1, 1);
        gridPane.add(multiplicityLabel, 0, 2);
        gridPane.add(multiplicityTextField, 1, 2);
        gridPane.add(multiplicityFunctionLabel, 0, 3);
        gridPane.add(multiplicityFunctionChoiceBox, 1, 3);
    }

    private void arcMultiplicityTypeChanged(ObservableValue<? extends ArcMultiplicityType> observableValue, ArcMultiplicityType oldValue, ArcMultiplicityType newValue) {
        if (oldValue == newValue) {
            return;
        }

        if (newValue == ArcMultiplicityType.CONSTANT) {
            multiplicityTextField.setDisable(false);
            multiplicityFunctionChoiceBox.setDisable(true);
        } else {
            multiplicityTextField.setDisable(true);
            multiplicityFunctionChoiceBox.setDisable(false);
        }
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        ArcViewModel arcViewModel = (ArcViewModel) viewModel;
        arcMultiplicityTypeChoiceBox.valueProperty().bindBidirectional(arcViewModel.multiplicityTypeProperty());
        multiplicityTextField.textProperty().bindBidirectional(arcViewModel.multiplicityProperty());
//        multiplicityFunctionChoiceBox.valueProperty().bindBidirectional(arcViewModel.multiplicityFunctionProperty());
    }

    @Override
    public void unbindViewModel() {
        ArcViewModel arcViewModel = (ArcViewModel) viewModel;
        arcMultiplicityTypeChoiceBox.valueProperty().unbindBidirectional(arcViewModel.multiplicityTypeProperty());
        multiplicityTextField.textProperty().unbindBidirectional(arcViewModel.multiplicityProperty());
//        multiplicityFunctionChoiceBox.valueProperty().unbindBidirectional(arcViewModel.multiplicityFunctionProperty());
        super.unbindViewModel();
    }

}
