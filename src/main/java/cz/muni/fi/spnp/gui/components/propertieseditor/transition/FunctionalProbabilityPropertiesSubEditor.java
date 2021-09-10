package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.FunctionalTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.TransitionProbabilityViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

public class FunctionalProbabilityPropertiesSubEditor extends TransitionProbabilitySubEditor {

    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;
    private Label functionLabel;
    private ChoiceBox<FunctionViewModel> functionChoiceBox;

    public FunctionalProbabilityPropertiesSubEditor() {
        createView();

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
    }

    private void createView() {
        functionLabel = new Label("Function:");
        functionChoiceBox = new ChoiceBox<>();

        addRow(functionLabel, functionChoiceBox);
    }

    private void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        var probabilityFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.Probability)
                .collect(Collectors.toList());
        var functionsCopy = FXCollections.observableArrayList(probabilityFunctions);
        functionsCopy.add(0, null);
        functionChoiceBox.setItems(functionsCopy);
    }

    @Override
    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        super.bindDiagramViewModel(diagramViewModel);

        functionChoiceBox.setConverter(new FunctionViewModelStringConverter(diagramViewModel));

        diagramViewModel.getFunctions().addListener(this.onFunctionsChangedListener);
        onFunctionsChangedListener(null);
    }

    @Override
    public void unbindDiagramViewModel() {
        diagramViewModel.getFunctions().removeListener(this.onFunctionsChangedListener);

        super.unbindDiagramViewModel();
    }

    @Override
    public void bindViewModel(TransitionProbabilityViewModel transitionProbabilityViewModel) {
        super.bindViewModel(transitionProbabilityViewModel);

        var functionalTransitionProbabilityViewModel = (FunctionalTransitionProbabilityViewModel) transitionProbabilityViewModel;
        functionChoiceBox.valueProperty().bindBidirectional(functionalTransitionProbabilityViewModel.functionProperty());
    }

    public void unbindViewModel() {
        var functionalTransitionProbabilityViewModel = (FunctionalTransitionProbabilityViewModel) viewModel;
        functionChoiceBox.valueProperty().unbindBidirectional(functionalTransitionProbabilityViewModel.functionProperty());

        super.unbindViewModel();
    }

}
