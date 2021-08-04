package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelUtils;

public class OperationSelectAll implements GraphElementsOperation {

    private final GraphView graphView;

    public OperationSelectAll(GraphView graphView) {
        this.graphView = graphView;
    }

    @Override
    public void execute() {
        var allViewModels = ViewModelUtils.includeDragPoints(graphView.getDiagramViewModel().getElements());
        graphView.getDiagramViewModel().select(allViewModels);
    }

}
