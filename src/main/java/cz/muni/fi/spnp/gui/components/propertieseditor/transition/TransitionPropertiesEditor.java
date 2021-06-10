package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.components.propertieseditor.IntegerTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.PositionableElementPropertiesEditor;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class TransitionPropertiesEditor extends PositionableElementPropertiesEditor {

    private final Label priorityLabel;
    private final IntegerTextField priorityTextField;
    private final Label guardFunctionLabel;
    private final TextField guardFunctionTextField;

    public TransitionPropertiesEditor() {
        priorityLabel = new Label("Priority:");
        priorityTextField = new IntegerTextField();
        guardFunctionLabel = new Label("Guard function:");
        guardFunctionTextField = new TextField();

        gridPane.add(priorityLabel, 0, 3);
        gridPane.add(priorityTextField.getTextField(), 1, 3);
        gridPane.add(guardFunctionLabel, 0, 4);
        gridPane.add(guardFunctionTextField, 1, 4);
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextFormatter().valueProperty().bindBidirectional((Property) transitionViewModel.priorityProperty());
        guardFunctionTextField.textProperty().bind(transitionViewModel.guardFunctionProperty());
    }

    @Override
    public void unbindViewModel() {
        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextFormatter().valueProperty().unbindBidirectional((Property) transitionViewModel.priorityProperty());
        guardFunctionTextField.textProperty().unbind();
        super.unbindViewModel();
    }

}
