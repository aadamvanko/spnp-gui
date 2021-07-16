package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.model.ClipboardOperationType;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;

public class OperationCutElements implements GraphElementsOperation {

    private final GraphView graphView;

    public OperationCutElements(GraphView graphView) {
        this.graphView = graphView;
    }

    @Override
    public void execute() {
        var selectedWithoutDragPoints = filterOutDragPoints(graphView.getSelected());
        var model = graphView.getModel();
        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(selectedWithoutDragPoints);
        model.setClipboardOperationType(ClipboardOperationType.CUT);

        graphView.getDiagramViewModel().getElements().removeAll(selectedWithoutDragPoints);
        graphView.getDiagramViewModel().getElements().removeIf(this::isDisconnectedArc);
    }

    private boolean isDisconnectedArc(ElementViewModel elementViewModel) {
        if (elementViewModel instanceof ArcViewModel) {
            var arcViewModel = (ArcViewModel) elementViewModel;
            var diagram = graphView.getDiagramViewModel();
            var containsFrom = diagram.getElements().contains(arcViewModel.getFromViewModel());
            var containsTo = diagram.getElements().contains(arcViewModel.getToViewModel());
            var containsExactlyOne = (containsFrom && !containsTo) || (!containsFrom && containsTo);
            return containsExactlyOne;
        }
        return false;
    }
}
