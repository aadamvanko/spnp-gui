package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.DragPointViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;

import java.util.ArrayList;

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
    }

}
