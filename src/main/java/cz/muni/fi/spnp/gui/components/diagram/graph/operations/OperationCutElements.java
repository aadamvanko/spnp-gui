package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Clipboard;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;

/**
 * Cut operation for the diagram's elements.
 */
public class OperationCutElements extends GraphElementsOperationBase {

    public OperationCutElements(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        var selectedWithoutDragPoints = filterOutDragPoints(diagramViewModel.getSelected());
        removeUncutPlaceReferences(selectedWithoutDragPoints);
        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(selectedWithoutDragPoints);
        model.getClipboard().getFunctions().clear();
        model.getClipboard().getFunctions().addAll(redirectFunctionReferences(selectedWithoutDragPoints, diagramViewModel, Clipboard.OperationType.CUT));
        model.getClipboard().setOperationType(Clipboard.OperationType.CUT);

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
