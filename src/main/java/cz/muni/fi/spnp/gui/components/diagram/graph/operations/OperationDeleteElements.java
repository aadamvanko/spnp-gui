package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DragPointUtils;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.DragPointViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Delete operation for the diagram's elements.
 */
public class OperationDeleteElements extends GraphElementsOperationBase implements GraphElementsOperation {

    public OperationDeleteElements(Model model, DiagramViewModel diagramViewModel) {
        super(model, diagramViewModel);
    }

    @Override
    public void execute() {
        // clear selection first, because removing from elements causes unbind and null pointer exception
        var selectedCopy = new ArrayList<>(diagramViewModel.getSelected());
        diagramViewModel.resetSelection();

        selectedCopy.forEach(elementViewModel -> {
            if (elementViewModel instanceof ArcViewModel) {
                ((ArcViewModel) elementViewModel).removeFlushFunctionChangeListener();
            }
        });
        diagramViewModel.getElements().removeAll(selectedCopy);

        // select the parent arc if possible
        ArcViewModel arcToSelect = null;
        if (!selectedCopy.isEmpty() && DragPointUtils.allSelectedAreSameArcDragPoints(diagramViewModel.getElements(), selectedCopy)) {
            var dragPoint = (DragPointViewModel) selectedCopy.get(0);
            arcToSelect = DragPointUtils.findArcByDragPoint(diagramViewModel.getElements(), dragPoint);
        }

        selectedCopy.stream()
                .filter(elementViewModel -> elementViewModel instanceof DragPointViewModel)
                .forEach(dragPointViewModel -> {
                    var arcs = ViewModelUtils.onlyElements(ArcViewModel.class, diagramViewModel.getElements());
                    arcs.forEach(arcViewModel -> {
                        arcViewModel.getDragPoints().remove(dragPointViewModel);
                        try {
                            arcViewModel.getRemoveStraightLinesCallback().call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                });
        diagramViewModel.removeDisconnectedArcs();

        if (arcToSelect != null) {
            diagramViewModel.select(List.of(arcToSelect));
        }
    }

}
