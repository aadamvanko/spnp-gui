package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.TransitionOrientation;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.ElementPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.IntegerTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MyChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.converter.IntegerStringConverter;

import java.util.stream.Collectors;

/**
 * Base class for all transition properties editors.
 */
public abstract class TransitionPropertiesEditor extends ElementPropertiesEditor {

    private final Label priorityLabel;
    private final IntegerTextField priorityTextField;
    private final Label guardFunctionLabel;
    private final MyChoiceBox<FunctionViewModel> guardFunctionChoiceBox;
    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;
    private final Label orientationLabel;
    private final ChoiceBox<TransitionOrientation> orientationChoiceBox;

    public TransitionPropertiesEditor() {
        priorityLabel = new Label("Priority:");
        priorityTextField = new IntegerTextField();
        addRow(priorityLabel, priorityTextField.getTextField());

        guardFunctionLabel = new Label("Guard function:");
        guardFunctionChoiceBox = new MyChoiceBox<>();
        addRow(guardFunctionLabel, guardFunctionChoiceBox);

        orientationLabel = new Label("Orientation:");
        orientationChoiceBox = new ChoiceBox<TransitionOrientation>();
        orientationChoiceBox.setItems(FXCollections.observableArrayList(TransitionOrientation.values()));
        addRow(orientationLabel, orientationChoiceBox);

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
    }

    @Override
    protected Class<?> getElementClassForDuplicity() {
        return TransitionViewModel.class;
    }

    private void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        var guardFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.Guard)
                .collect(Collectors.toList());
        var functionsCopy = FXCollections.observableArrayList(guardFunctions);
        functionsCopy.add(0, null);
        guardFunctionChoiceBox.setItemsWithSelected(functionsCopy);
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
        priorityTextField.getTextField().textProperty().bindBidirectional(transitionViewModel.priorityProperty().asObject(), new IntegerStringConverter());
        guardFunctionChoiceBox.valueProperty().bindBidirectional(transitionViewModel.guardFunctionProperty());
        guardFunctionChoiceBox.getSelectionModel().select(transitionViewModel.getGuardFunction());
        orientationChoiceBox.valueProperty().bindBidirectional(transitionViewModel.orientationProperty());
    }

    @Override
    public void unbindViewModel() {
        TransitionViewModel transitionViewModel = (TransitionViewModel) viewModel;
        priorityTextField.getTextField().textProperty().unbindBidirectional(transitionViewModel.priorityProperty().asObject());
        guardFunctionChoiceBox.valueProperty().unbindBidirectional(transitionViewModel.guardFunctionProperty());
        orientationChoiceBox.valueProperty().unbindBidirectional(transitionViewModel.orientationProperty());

        super.unbindViewModel();
    }

}
