package cz.muni.fi.spnp.gui.components.diagram.graph.common;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.DragPointViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class providing operations with the drag point view model collections.
 */
public final class DragPointUtils {

    private DragPointUtils() {
    }

    public static ArcViewModel findArcByDragPoint(List<ElementViewModel> elements, DragPointViewModel dragPoint) {
        var arcs = ViewModelUtils.onlyElements(ArcViewModel.class, elements)
                .collect(Collectors.toList());
        for (var arc : arcs) {
            if (arc.getDragPoints().contains(dragPoint)) {
                return arc;
            }
        }
        return null;
    }

    public static boolean allSelectedAreSameArcDragPoints(List<ElementViewModel> elements, List<ElementViewModel> selectedElements) {
        var allAreDragPoints = selectedElements.stream().allMatch(viewModel -> viewModel instanceof DragPointViewModel);
        if (!allAreDragPoints) {
            return false;
        }

        var firstArc = findArcByDragPoint(elements, (DragPointViewModel) selectedElements.get(0));
        var allHaveSameArc = selectedElements.stream()
                .allMatch(viewModel -> findArcByDragPoint(elements, (DragPointViewModel) viewModel) == firstArc);
        return allHaveSameArc;
    }

}
