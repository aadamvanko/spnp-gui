package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.ConnectablePropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.propertieseditor.IntegerTextField;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

public abstract class TransitionPropertiesEditor extends ConnectablePropertiesEditor {

    private final Label priorityLabel;
    private final IntegerTextField priorityTextField;
    private final Label guardFunctionLabel;
    private final ChoiceBox<FunctionViewModel> guardFunctionChoiceBox;
    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;

    public TransitionPropertiesEditor() {
        priorityLabel = new Label("Priority:");
        priorityTextField = new IntegerTextField();
        guardFunctionLabel = new Label("Guard function:");
        guardFunctionChoiceBox = new ChoiceBox<>();
        guardFunctionChoiceBox.setMinWidth(100);

        addRow(priorityLabel, priorityTextField.getTextField());
        addRow(guardFunctionLabel, guardFunctionChoiceBox);

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
    }

    public void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        var guardFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.Guard)
                .collect(Collectors.toList());
        var functionsCopy = FXCollections.observableArrayList(guardFunctions);
        functionsCopy.add(0, null);
        guardFunctionChoiceBox.setItems(functionsCopy);
    }


    @Override
    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        super.bindDiagramViewModel(diagramViewModel);

        guardFunctionChoiceBox.setConverter(new FunctionViewModelStringConverter(diagramViewModel));

        diagramViewModel.getFunctions().addListener(this.onFunctionsChangedListener);
        onFunctionsChangedListener(null);
    }

    @Override
    public void unbindDiagramViewModel() {
        diagramViewModel.getFunctions().removeListener(this.onFunctionsChangedListener);

        super.unbindDiagramViewModel();
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextFormatter().valueProperty().bindBidirectional((Property) transitionViewModel.priorityProperty());
        guardFunctionChoiceBox.valueProperty().bindBidirectional(transitionViewModel.guardFunctionProperty());
        guardFunctionChoiceBox.getSelectionModel().select(transitionViewModel.getGuardFunction());
    }

    @Override
    public void unbindViewModel() {
        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextFormatter().valueProperty().unbindBidirectional((Property) transitionViewModel.priorityProperty());
        guardFunctionChoiceBox.valueProperty().unbindBidirectional(transitionViewModel.guardFunctionProperty());

        super.unbindViewModel();
    }

}
