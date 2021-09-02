package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.model.ClipboardOperationType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;

public class OperationCutElements extends GraphElementsOperationBase implements GraphElementsOperation {

    public OperationCutElements(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        var selectedWithoutDragPoints = filterOutDragPoints(diagramViewModel.getSelected());
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
            var containsFrom = diagramViewModel.getElements().contains(arcViewModel.getFromViewModel());
            var containsTo = diagramViewModel.getElements().contains(arcViewModel.getToViewModel());
            var containsExactlyOne = (containsFrom && !containsTo) || (!containsFrom && containsTo);
            return containsExactlyOne;
        }
        return false;
    }
}
