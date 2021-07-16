package cz.muni.fi.spnp.gui.viewmodel;

import java.util.List;
import java.util.stream.Stream;

public final class ViewModelUtils {

    public static <T> Stream<T> onlyElements(Class<T> viewModelClass, List<ElementViewModel> elements) {
        return elements.stream()
                .filter(viewModelClass::isInstance)
                .map(viewModelClass::cast);
    }

}
