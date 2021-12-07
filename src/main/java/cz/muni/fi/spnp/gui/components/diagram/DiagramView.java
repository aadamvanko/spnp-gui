package cz.muni.fi.spnp.gui.components.diagram;

import cz.muni.fi.spnp.gui.components.diagram.codeview.CodeView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * View of the diagram showing the diagram's content either in graph editor or as a code preview (CSPL).
 */
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
