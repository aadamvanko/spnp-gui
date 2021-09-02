package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DragPointViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelUtils;

import java.util.ArrayList;

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
