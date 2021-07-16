package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;

public class OperationPasteElements implements GraphElementsOperation {

    private final GraphView graphView;

    public OperationPasteElements(GraphView graphView) {
        this.graphView = graphView;
    }

    @Override
    public void execute() {
        var model = graphView.getModel();
        switch (model.getClipboardOperationType()) {
            case COPY:
                var newCopies = createViewModelsCopies(model.getClipboardElements());
                graphView.getDiagramViewModel().getElements().addAll(newCopies);
                graphView.selectViewModels(newCopies);
                break;

            case CUT:
                graphView.getDiagramViewModel().getElements().addAll(model.getClipboardElements());
                graphView.selectViewModels(model.getClipboardElements());
                model.getClipboardElements().clear();
                break;
        }
    }
}
