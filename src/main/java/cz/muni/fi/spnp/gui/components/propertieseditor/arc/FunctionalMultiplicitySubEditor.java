package cz.muni.fi.spnp.gui.components.propertieseditor.arc;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.FunctionViewModelStringConverter;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MyChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;

import java.util.stream.Collectors;

/**
 * Subeditor for the functional multiplicity of the arc.
 */
public class FunctionalMultiplicitySubEditor extends ArcMultiplicitySubEditor {

    private Label multiplicityFunctionLabel;
    private MyChoiceBox<FunctionViewModel> multiplicityFunctionChoiceBox;

    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;

    public FunctionalMultiplicitySubEditor() {
        createView();

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
    }

    private void createView() {
        multiplicityFunctionLabel = new Label("Multiplicity function:");
        multiplicityFunctionChoiceBox = new MyChoiceBox<>();
        addRow(multiplicityFunctionLabel, multiplicityFunctionChoiceBox);
    }

    private void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        var arcCardinalityFunctions = diagramViewModel.getFunctions().stream()
                .filter(functionViewModel -> functionViewModel.getFunctionType() == FunctionType.ArcCardinality)
                .filter(functionViewModel -> functionViewModel.isVisible())
                .collect(Collectors.toList());
//        System.out.println("changed");
        var functionsCopy = FXCollections.observableArrayList(arcCardinalityFunctions);
        functionsCopy.add(0, null);
        multiplicityFunctionChoiceBox.setItemsWithSelected(functionsCopy);
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
        multiplicityFunctionChoiceBox.disableProperty().bind(arcViewModel.isFlushingProperty());
    }

    @Override
    public void unbindViewModel() {
        multiplicityFunctionChoiceBox.valueProperty().unbindBidirectional(viewModel.multiplicityFunctionProperty());
        multiplicityFunctionChoiceBox.disableProperty().unbind();

        super.unbindViewModel();
    }

}
