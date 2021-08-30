package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public abstract class ConnectablePropertiesEditor extends ElementPropertiesEditor {

    protected final Label positionXLabel;
    protected final IntegerTextField positionXTextField;
    protected final Label positionYLabel;
    protected final IntegerTextField positionYTextField;

    private String oldName;
    private StringProperty oldNameProperty;
    private DiagramViewModel oldDiagramViewModel;

    public ConnectablePropertiesEditor() {
        positionXLabel = new Label("Position X:");
        positionXTextField = new IntegerTextField();
        positionYLabel = new Label("Position Y:");
        positionYTextField = new IntegerTextField();

        addRow(positionXLabel, positionXTextField.getTextField());
        addRow(positionYLabel, positionYTextField.getTextField());

        nameTextField.focusedProperty().addListener(this::onNameTextFieldFocusChangedListener);
    }

    private void onNameTextFieldFocusChangedListener(Observable observable, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
            if (oldDiagramViewModel.isElementNameClassDuplicit(nameTextField.getText(), getElementClassForDuplicity())) {
                DialogMessages.showError("This name is already used!");
                oldNameProperty.set(oldName);
            }
        }
    }

    protected abstract Class<?> getElementClassForDuplicity();

    @Override
    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        super.bindDiagramViewModel(diagramViewModel);

        oldDiagramViewModel = diagramViewModel;
    }

    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(positionXTextField.getTextField().textProperty(), getViewModel().positionXProperty(), converter);
        Bindings.bindBidirectional(positionYTextField.getTextField().textProperty(), getViewModel().positionYProperty(), converter);

        oldName = viewModel.getName();
        oldNameProperty = viewModel.nameProperty();
    }

    public void unbindViewModel() {
        Bindings.unbindBidirectional(positionXTextField.getTextField().textProperty(), getViewModel().positionXProperty());
        Bindings.unbindBidirectional(positionYTextField.getTextField().textProperty(), getViewModel().positionYProperty());

        // TODO adjust canvas size, prevent too small values

        super.unbindViewModel();
    }

    @Override
    protected ConnectableViewModel getViewModel() {
        return (ConnectableViewModel) viewModel;
    }

}
