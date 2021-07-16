package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.model.ClipboardOperationType;

public class OperationCopyElements implements GraphElementsOperation {

    private final GraphView graphView;

    public OperationCopyElements(GraphView graphView) {
        this.graphView = graphView;
    }

    @Override
    public void execute() {
        var selectedWithoutDragPoints = filterOutDragPoints(graphView.getSelected());
        var model = graphView.getModel();
        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(createViewModelsCopies(selectedWithoutDragPoints));
        model.setClipboardOperationType(ClipboardOperationType.COPY);
    }

}
