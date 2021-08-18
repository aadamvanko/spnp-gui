package cz.muni.fi.spnp.gui.viewmodel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static String createFlushFunctionBody(String arcName) {
        return String.format("return mark(\"%s\");", arcName);
    }

}
