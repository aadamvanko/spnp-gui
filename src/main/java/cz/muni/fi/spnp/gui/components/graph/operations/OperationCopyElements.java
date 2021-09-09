package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.model.Clipboard;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class OperationCopyElements extends GraphElementsOperationBase {

    public OperationCopyElements(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        var selectedWithoutDragPoints = filterOutDragPoints(diagramViewModel.getSelected());
        model.getClipboardElements().clear();
        model.getClipboardElements().addAll(createViewModelsCopies(selectedWithoutDragPoints));
        model.getClipboard().setOperationType(Clipboard.OperationType.COPY);
    }

}
