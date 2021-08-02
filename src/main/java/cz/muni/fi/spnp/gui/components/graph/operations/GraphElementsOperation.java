package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface GraphElementsOperation {

    void execute();

    default List<ElementViewModel> filterOutDragPoints(List<ElementViewModel> selected) {
        return selected.stream()
                .filter(elementViewModel -> !(elementViewModel instanceof DragPointViewModel))
                .collect(Collectors.toList());
    }

    default List<ElementViewModel> createViewModelsCopies(List<ElementViewModel> selectedWithoutDragPoints) {
        var viewModelCopyFactory = new ViewModelCopyFactory();
        var copiesPlaces = ViewModelUtils.onlyElements(PlaceViewModel.class, selectedWithoutDragPoints).map(viewModelCopyFactory::createCopy);
        var copiesTransitions = ViewModelUtils.onlyElements(TransitionViewModel.class, selectedWithoutDragPoints).map(viewModelCopyFactory::createCopy);
        var copiesArcs = ViewModelUtils.onlyElements(ArcViewModel.class, selectedWithoutDragPoints).map(viewModelCopyFactory::createCopy);
        return Stream.concat(Stream.concat(copiesPlaces, copiesTransitions), copiesArcs).collect(Collectors.toList());
    }

}
