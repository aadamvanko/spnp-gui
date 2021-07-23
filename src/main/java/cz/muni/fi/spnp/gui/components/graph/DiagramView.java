package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class DiagramView {

    private final GraphView graphView;
    private final CodeView codeView;
    private DiagramViewModel diagramViewModel;

    public DiagramView(Notifications notifications, Model model, DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        this.graphView = new GraphView(notifications, model, diagramViewModel);
        this.codeView = new CodeView(diagramViewModel);
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
        diagramViewModel = null;
    }

}