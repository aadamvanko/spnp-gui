package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;

public class OperationSelectAll implements GraphElementsOperation {

    private final GraphView graphView;

    public OperationSelectAll(GraphView graphView) {
        this.graphView = graphView;
    }

    @Override
    public void execute() {
        graphView.selectViewModels(graphView.getDiagramViewModel().getElements());
    }

}
