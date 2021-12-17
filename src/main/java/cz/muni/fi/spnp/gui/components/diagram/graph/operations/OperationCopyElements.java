package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Clipboard;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;

/**
 * Copy operation on the diagram's elements.
 */
public class OperationCopyElements extends GraphElementsOperationBase {

    public OperationCopyElements(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        var selectedWithoutDragPoints = filterOutDragPoints(diagramViewModel.getSelected());
        selectedWithoutDragPoints = filterOutDisconnectedArcs(selectedWithoutDragPoints);
        removeUncutPlaceReferences(selectedWithoutDragPoints);

        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(createViewModelsCopies(selectedWithoutDragPoints));
        model.getClipboard().getFunctions().clear();
        model.getClipboard().getFunctions().addAll(redirectFunctionReferences(selectedWithoutDragPoints, diagramViewModel, Clipboard.OperationType.COPY));
        model.getClipboard().setOperationType(Clipboard.OperationType.COPY);
    }

}
