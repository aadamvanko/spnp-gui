package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.StandardArcViewModel;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class ArcPropertiesEditor extends PropertiesEditor {

    private final Label multiplicityLabel;
    private final IntegerTextField multiplicityTextField;
    private final Label multiplicityFunctionLabel;
    private final ChoiceBox<String> multiplicityFunctionChoiceBox;

    public ArcPropertiesEditor() {
        multiplicityLabel = new Label("Multiplicty:");
        multiplicityTextField = new IntegerTextField();
        multiplicityFunctionLabel = new Label("Multiplicity function:");
        multiplicityFunctionChoiceBox = new ChoiceBox<>();

        gridPane.add(multiplicityLabel, 0, 1);
        gridPane.add(multiplicityTextField.getTextField(), 1, 1);
        gridPane.add(multiplicityFunctionLabel, 0, 2);
        gridPane.add(multiplicityFunctionChoiceBox, 1, 2);
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        StandardArcViewModel standardArcViewModel = (StandardArcViewModel) viewModel;
    }

    @Override
    public void unbindViewModel() {
        StandardArcViewModel standardArcViewModel = (StandardArcViewModel) viewModel;
        super.unbindViewModel();
    }

}
