package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class providing mainly search and filter capabilities on the view models collections.
 */
public final class ViewModelUtils {

    public static <T> Stream<T> onlyElements(Class<T> viewModelClass, List<ElementViewModel> elements) {
        return elements.stream()
                .filter(viewModelClass::isInstance)
                .map(viewModelClass::cast);
    }

    public static List<ElementViewModel> includeDragPoints(List<ElementViewModel> elementViewModels) {
        var extractedDragPoints = extractDragPoints(elementViewModels);
        return Stream.concat(elementViewModels.stream(), extractedDragPoints)
                .collect(Collectors.toList());
    }

    private static Stream<ElementViewModel> extractDragPoints(List<ElementViewModel> elementViewModels) {
        return elementViewModels.stream()
                .filter(viewModel -> viewModel instanceof ArcViewModel)
                .map(viewModel -> (ArcViewModel) viewModel)
                .flatMap(arcViewModel -> arcViewModel.getDragPoints().stream());
    }

    public static FunctionViewModel findFunctionByName(List<FunctionViewModel> functions, String functionName) {
        return functions.stream()
                .filter(functionViewModel -> functionViewModel.getName().equals(functionName))
                .findAny()
                .orElse(null);
    }

    public static String createFlushFunctionBody(String placeName) {
        return String.format("return mark(\"%s\");", placeName);
    }

}
