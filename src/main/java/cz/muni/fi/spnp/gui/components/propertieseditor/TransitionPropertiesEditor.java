package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.Debug;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.TransitionViewModel;
import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class TransitionPropertiesEditor extends PropertiesEditor {

    private final Label priorityLabel;
    private final IntegerTextField priorityTextField;
    private final Label guardFunctionLabel;
    private final TextField guardFunctionTextField;

    public TransitionPropertiesEditor() {
        priorityLabel = new Label("Prioritykkkkasdasdasdasdasdaskkkkkkk:");
        Debug.addGreenBg(priorityLabel);
        priorityTextField = new IntegerTextField();
        guardFunctionLabel = new Label("I DONT SEE THIS");
        guardFunctionTextField = new TextField();

        Debug.addYellowBg(guardFunctionLabel);
//        Debug.addBlueBg(guardFunctionTextField);


        gridPane.add(priorityLabel, 0, 1);
        gridPane.add(priorityTextField.getTextField(), 1, 1);
        gridPane.add(guardFunctionLabel, 0, 2);
        gridPane.add(guardFunctionTextField, 1, 2);
        gridPane.add(new Label("USELESS"), 0, 3);
        gridPane.add(new TextField(), 1, 3);
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);
        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextFormatter().valueProperty().bindBidirectional((Property) transitionViewModel.priorityProperty());
        guardFunctionLabel.textProperty().bind(transitionViewModel.guardFunctionProperty());
    }

    @Override
    public void unbindViewModel() {
        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextFormatter().valueProperty().unbindBidirectional((Property) transitionViewModel.priorityProperty());
        guardFunctionLabel.textProperty().unbind();
        super.unbindViewModel();
    }

}
