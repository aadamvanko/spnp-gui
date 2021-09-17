package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DiagramView {

    private final GraphView graphView;
    private final CodeView codeView;
    private DiagramViewModel diagramViewModel;
    private final ChangeListener<Boolean> onNeedsCodeRefreshChangeListener;

    public DiagramView(Model model, DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;

        this.onNeedsCodeRefreshChangeListener = this::onNeedsCodeRefreshChangeListener;

        this.graphView = new GraphView(model, diagramViewModel);
        this.codeView = new CodeView(model, diagramViewModel);

        diagramViewModel.needsCodeRefreshProperty().addListener(this.onNeedsCodeRefreshChangeListener);
    }

    private void onNeedsCodeRefreshChangeListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            codeView.prepare();
        }
    }

    public GraphView getGraphView() {
        return graphView;
    }

    public CodeView getCodeView() {
        return codeView;
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public void unbindViewModels() {
        diagramViewModel.needsCodeRefreshProperty().removeListener(this.onNeedsCodeRefreshChangeListener);

        diagramViewModel = null;
        graphView.unbindViewModels();
        codeView.unbindViewModels();
    }

}
