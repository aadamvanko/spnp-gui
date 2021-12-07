package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.ArcViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConstantMultiplicitySubEditor extends ArcMultiplicitySubEditor {

    private Label multiplicityLabel;
    private TextField multiplicityTextField;

    public ConstantMultiplicitySubEditor() {
        createView();
    }

    private void createView() {
        multiplicityLabel = new Label("Multiplicity:");
        multiplicityTextField = new TextField();
        addRow(multiplicityLabel, multiplicityTextField);
    }

    @Override
    public void bindViewModel(ArcViewModel arcViewModel) {
        super.bindViewModel(arcViewModel);

        multiplicityTextField.textProperty().bindBidirectional(arcViewModel.multiplicityProperty());
    }

    @Override
    public void unbindViewModel() {
        multiplicityTextField.textProperty().unbindBidirectional(viewModel.multiplicityProperty());

        super.unbindViewModel();
    }

}
