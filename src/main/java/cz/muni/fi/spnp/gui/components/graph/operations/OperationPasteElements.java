package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;

import java.util.List;

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
                renameIfNeeded(graphView.getDiagramViewModel(), newCopies);
                offsetElements(newCopies);
                graphView.getDiagramViewModel().getElements().addAll(newCopies);
                graphView.selectViewModels(newCopies);
                break;

            case CUT:
                renameIfNeeded(graphView.getDiagramViewModel(), model.getClipboardElements());
                offsetElements(model.getClipboardElements());
                graphView.getDiagramViewModel().getElements().addAll(model.getClipboardElements());
                graphView.selectViewModels(model.getClipboardElements());
                model.getClipboardElements().clear();
                break;
        }
    }

    private void offsetElements(List<ElementViewModel> elements) {
        final double offsetX = 20;
        final double offsetY = 20;

        elements.stream()
                .filter(elementViewModel -> elementViewModel instanceof ConnectableViewModel)
                .map(elementViewModel -> (ConnectableViewModel) elementViewModel)
                .forEach(elementViewModel -> {
                    elementViewModel.positionXProperty().set(elementViewModel.getPositionX() + offsetX);
                    elementViewModel.positionYProperty().set(elementViewModel.getPositionY() + offsetY);
                });
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
        var model = graphView.getModel();
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
