package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.*;

import java.util.List;

public class OperationPasteElements extends GraphElementsOperationBase implements GraphElementsOperation {

    public OperationPasteElements(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        switch (model.getClipboardOperationType()) {
            case COPY:
                var newCopies = createViewModelsCopies(model.getClipboardElements());
                renameIfNeeded(diagramViewModel, newCopies);
                offsetElements(newCopies);
                diagramViewModel.getElements().addAll(newCopies);
                diagramViewModel.select(ViewModelUtils.includeDragPoints(newCopies));
                break;

            case CUT:
                renameIfNeeded(diagramViewModel, model.getClipboardElements());
                offsetElements(model.getClipboardElements());
                diagramViewModel.getElements().addAll(model.getClipboardElements());
                diagramViewModel.select(ViewModelUtils.includeDragPoints(model.getClipboardElements()));
                model.getClipboardElements().clear();
                break;
        }
    }

    private void offsetElements(List<ElementViewModel> elements) {
        final double offsetX = 20;
        final double offsetY = 20;

        for (var element : elements) {
            if (element instanceof ConnectableViewModel) {
                var connectableViewModel = (ConnectableViewModel) element;
                connectableViewModel.positionXProperty().set(connectableViewModel.getPositionX() + offsetX);
                connectableViewModel.positionYProperty().set(connectableViewModel.getPositionY() + offsetY);
            } else if (element instanceof ArcViewModel) {
                var arcViewModel = (ArcViewModel) element;
                arcViewModel.getDragPoints().forEach(dragPointViewModel -> {
                    dragPointViewModel.positionXProperty().set(dragPointViewModel.getPositionX() + offsetX);
                    dragPointViewModel.positionYProperty().set(dragPointViewModel.getPositionY() + offsetY);
                });
            }
        }
    }

    private void renameIfNeeded(DiagramViewModel diagramViewModel, List<ElementViewModel> newCopies) {
        for (var copy : newCopies) {
            int id = 1;
            var oldName = copy.getName();
            while (diagramViewModel.containsElementNameType(copy)) {
                var suffix = id == 1 ? String.format("_%s", getSuffixWord()) : String.format("_%s_%d", getSuffixWord(), id);
                copy.nameProperty().set(oldName + suffix);
                id++;
            }
        }
    }

    private String getSuffixWord() {
        switch (model.getClipboardOperationType()) {
            case COPY:
                return "copy";
            case CUT:
                return "moved";
            default:
                throw new AssertionError("Unsupported clipboard operation type " + model.getClipboardOperationType());
        }
    }

}
