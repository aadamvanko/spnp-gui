package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionalTransitionDistributionSubEditor extends TransitionDistributionSubEditor {

    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;
    private List<Label> valuesLabels;
    private List<ChoiceBox<FunctionViewModel>> functionsChoiceBoxes;

    public FunctionalTransitionDistributionSubEditor() {
        createView();

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
    }

    private void createView() {
        valuesLabels = new ArrayList<>();
        functionsChoiceBoxes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            var valueLabel = new Label("Value:");
            valuesLabels.add(valueLabel);
            var functionChoiceBox = new ChoiceBox<FunctionViewModel>();
            functionsChoiceBoxes.add(functionChoiceBox);
            addRow(valueLabel, functionChoiceBox);
        }
    }

    private void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        var distributionFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.Distribution)
                .collect(Collectors.toList());
        var functionsCopy = FXCollections.observableArrayList(distributionFunctions);
        functionsCopy.add(0, null);
        functionsChoiceBoxes.forEach(functionChoiceBox -> functionChoiceBox.setItems(functionsCopy));
    }

    @Override
    public List<PropertiesEditorRow> getRows() {
        return super.getRows().stream()
                .limit(viewModel.getValues().size())
                .collect(Collectors.toList());
    }

    @Override
    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        super.bindDiagramViewModel(diagramViewModel);

        functionsChoiceBoxes.forEach(functionChoiceBox -> functionChoiceBox.setConverter(new FunctionViewModelStringConverter(diagramViewModel)));

        diagramViewModel.getFunctions().addListener(this.onFunctionsChangedListener);
        onFunctionsChangedListener(null);
    }

    @Override
    public void unbindDiagramViewModel() {
        diagramViewModel.getFunctions().removeListener(this.onFunctionsChangedListener);

        super.unbindDiagramViewModel();
    }

    @Override
    public void bindViewModel(TransitionDistributionViewModel transitionDistributionViewModel) {
        super.bindViewModel(transitionDistributionViewModel);

        for (int i = 0; i < transitionDistributionViewModel.getValues().size(); i++) {
            valuesLabels.get(i).setText(String.format("%s function:", transitionDistributionViewModel.getValuesNames().get(i)));
            functionsChoiceBoxes.get(i).valueProperty().bindBidirectional(transitionDistributionViewModel.getFunctions().get(i));
        }
    }

    @Override
    public void unbindViewModel() {
        for (int i = 0; i < viewModel.getValues().size(); i++) {
            functionsChoiceBoxes.get(i).valueProperty().unbindBidirectional(viewModel.getFunctions().get(i));
        }

        super.unbindViewModel();
    }

}
