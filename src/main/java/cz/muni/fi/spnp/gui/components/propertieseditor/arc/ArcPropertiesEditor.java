package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.gui.components.propertieseditor.IntegerTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.PropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ArcMultiplicityType;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class ArcPropertiesEditor extends PropertiesEditor {

    private final Label arcMultiplicityTypeLabel;
    private final ChoiceBox<ArcMultiplicityType> arcMultiplicityTypeChoiceBox;
    private final Label multiplicityLabel;
    private final IntegerTextField multiplicityTextField;
    private final Label multiplicityFunctionLabel;
    private final ChoiceBox<String> multiplicityFunctionChoiceBox;

    public ArcPropertiesEditor() {
        arcMultiplicityTypeLabel = new Label("Multiplicity type:");
        var arcMultiplicityTypes = FXCollections.observableArrayList(ArcMultiplicityType.CONSTANT, ArcMultiplicityType.FUNCTION);
        arcMultiplicityTypeChoiceBox = new ChoiceBox<>(arcMultiplicityTypes);
        multiplicityLabel = new Label("Multiplicity:");
        multiplicityTextField = new IntegerTextField();
        multiplicityFunctionLabel = new Label("Multiplicity function:");
        multiplicityFunctionChoiceBox = new ChoiceBox<>();

        arcMultiplicityTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::arcMultiplicityTypeChanged);

        gridPane.add(arcMultiplicityTypeLabel, 0, 1);
        gridPane.add(arcMultiplicityTypeChoiceBox, 1, 1);
        gridPane.add(multiplicityLabel, 0, 2);
        gridPane.add(multiplicityTextField.getTextField(), 1, 2);
        gridPane.add(multiplicityFunctionLabel, 0, 3);
        gridPane.add(multiplicityFunctionChoiceBox, 1, 3);
    }

    private void arcMultiplicityTypeChanged(ObservableValue<? extends ArcMultiplicityType> observableValue, ArcMultiplicityType oldValue, ArcMultiplicityType newValue) {
        if (oldValue == newValue) {
            return;
        }

        if (newValue == ArcMultiplicityType.CONSTANT) {
            multiplicityTextField.getTextField().setDisable(false);
            multiplicityFunctionChoiceBox.setDisable(true);
        } else {
            multiplicityTextField.getTextField().setDisable(true);
            multiplicityFunctionChoiceBox.setDisable(false);
        }
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        ArcViewModel arcViewModel = (ArcViewModel) viewModel;
        arcMultiplicityTypeChoiceBox.valueProperty().bindBidirectional(arcViewModel.multiplicityTypeProperty());
        multiplicityTextField.getTextFormatter().valueProperty().bindBidirectional((Property) arcViewModel.multiplicityProperty());
        multiplicityFunctionChoiceBox.valueProperty().bindBidirectional(arcViewModel.multiplicityFunctionProperty());
    }

    @Override
    public void unbindViewModel() {
        ArcViewModel arcViewModel = (ArcViewModel) viewModel;
        arcMultiplicityTypeChoiceBox.valueProperty().unbindBidirectional(arcViewModel.multiplicityTypeProperty());
        multiplicityTextField.getTextFormatter().valueProperty().unbindBidirectional((Property) arcViewModel.multiplicityProperty());
        multiplicityFunctionChoiceBox.valueProperty().unbindBidirectional(arcViewModel.multiplicityFunctionProperty());
        super.unbindViewModel();
    }

}
