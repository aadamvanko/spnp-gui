package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
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
        selectedWithoutDragPoints = filterOutDisconnectedArcs(selectedWithoutDragPoints);
        removeUncutPlaceReferences(selectedWithoutDragPoints);
        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(selectedWithoutDragPoints);
        model.getClipboard().getFunctions().clear();
        model.getClipboard().getFunctions().addAll(redirectFunctionReferences(selectedWithoutDragPoints, diagramViewModel, Clipboard.OperationType.CUT));
        model.getClipboard().setOperationType(Clipboard.OperationType.CUT);

        diagramViewModel.getSelected().removeAll(selectedWithoutDragPoints);
        diagramViewModel.getElements().removeAll(selectedWithoutDragPoints);
        diagramViewModel.getElements().removeIf(elementViewModel -> isDisconnectedArc(diagramViewModel.getElements(), elementViewModel));
    }

}
