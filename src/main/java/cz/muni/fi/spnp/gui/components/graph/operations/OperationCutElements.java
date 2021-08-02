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
        var diagramViewModel = graphView.getDiagramViewModel();
        var selectedWithoutDragPoints = filterOutDragPoints(diagramViewModel.getSelected());
        var model = graphView.getModel();
        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(selectedWithoutDragPoints);
        model.setClipboardOperationType(ClipboardOperationType.CUT);

        diagramViewModel.getSelected().removeAll(selectedWithoutDragPoints);
        diagramViewModel.getElements().removeAll(selectedWithoutDragPoints);
        diagramViewModel.getElements().removeIf(this::isDisconnectedArc);
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
