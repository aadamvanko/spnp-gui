package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

public class FunctionalMultiplicitySubEditor extends ArcMultiplicitySubEditor {

    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;
    private Label multiplicityFunctionLabel;
    private ChoiceBox<FunctionViewModel> multiplicityFunctionChoiceBox;

    public FunctionalMultiplicitySubEditor() {
        createView();

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
    }

    private void createView() {
        multiplicityFunctionLabel = new Label("Multiplicity function:");
        multiplicityFunctionChoiceBox = new ChoiceBox<>();
        addRow(multiplicityFunctionLabel, multiplicityFunctionChoiceBox);
    }

    private void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        var arcCardinalityFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.ArcCardinality)
                .collect(Collectors.toList());
        var functionsCopy = FXCollections.observableArrayList(arcCardinalityFunctions);
        functionsCopy.add(0, null);
        multiplicityFunctionChoiceBox.setItems(functionsCopy);
    }

    @Override
    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        super.bindDiagramViewModel(diagramViewModel);

        multiplicityFunctionChoiceBox.setConverter(new FunctionViewModelStringConverter(diagramViewModel));

        diagramViewModel.getFunctions().addListener(this.onFunctionsChangedListener);
        onFunctionsChangedListener(null);
    }

    @Override
    public void unbindDiagramViewModel() {
        diagramViewModel.getFunctions().removeListener(this.onFunctionsChangedListener);

        super.unbindDiagramViewModel();
    }

    @Override
    public void bindViewModel(ArcViewModel arcViewModel) {
        super.bindViewModel(arcViewModel);

        multiplicityFunctionChoiceBox.valueProperty().bindBidirectional(arcViewModel.multiplicityFunctionProperty());
    }

    @Override
    public void unbindViewModel() {
        multiplicityFunctionChoiceBox.valueProperty().unbindBidirectional(viewModel.multiplicityFunctionProperty());

        super.unbindViewModel();
    }

}