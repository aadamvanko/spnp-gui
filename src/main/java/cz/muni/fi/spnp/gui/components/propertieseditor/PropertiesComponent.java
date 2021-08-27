package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.ViewContainer;
import cz.muni.fi.spnp.gui.components.propertieseditor.arc.InhibitorArcPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.arc.StandardArcPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.place.PlacePropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.transition.ImmediateTransitionPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.transition.TimedTransitionPropertiesEditor;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class PropertiesComponent extends ViewContainer {

    private final Map<Class<?>, ElementPropertiesEditor> editors;
    private Class<?> currentType;
    private ElementPropertiesEditor currentEditor;
    private final ListChangeListener<ElementViewModel> onSelectedChangedListener;
    private DiagramViewModel diagramViewModel;

    public PropertiesComponent(Model model) {
        super(model, "Properties");

        editors = new HashMap<>();
        editors.put(null, new PropertiesEditorPlaceholder());
        editors.put(PlaceViewModel.class, new PlacePropertiesEditor());
        editors.put(StandardArcViewModel.class, new StandardArcPropertiesEditor());
        editors.put(InhibitorArcViewModel.class, new InhibitorArcPropertiesEditor());
        editors.put(ImmediateTransitionViewModel.class, new ImmediateTransitionPropertiesEditor());
        editors.put(TimedTransitionViewModel.class, new TimedTransitionPropertiesEditor());

        createView();

        this.onSelectedChangedListener = this::onSelectedChangedListener;

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
    }

    private void createView() {
        root.setContent(editors.get(null).getRoot());
        buttonAdd.setVisible(false);
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        diagramViewModel = newDiagram;

        if (oldDiagram != null) {
            oldDiagram.getSelected().removeListener(this.onSelectedChangedListener);
        }

        if (newDiagram != null) {
            newDiagram.getSelected().addListener(this.onSelectedChangedListener);
            setCorrectEditor(newDiagram.getSelected());
        }
    }

    private void onSelectedChangedListener(ListChangeListener.Change<? extends ElementViewModel> selectedChange) {
        setCorrectEditor(selectedChange.getList());
    }

    private void setCorrectEditor(ObservableList<? extends ElementViewModel> selectedViewModels) {
        if (selectedViewModels.size() == 1) {
            var elementViewModel = selectedViewModels.get(0);
            if (elementViewModel instanceof DragPointViewModel) {
                var dragPointViewModel = elementViewModel;
                elementViewModel = diagramViewModel.getElements().stream()
                        .filter(viewModel -> viewModel instanceof ArcViewModel)
                        .filter(arcViewModel -> ((ArcViewModel) arcViewModel).getDragPoints().contains(dragPointViewModel))
                        .findFirst().get();
            }

            if (elementViewModel.getClass().equals(currentType)) {
                currentEditor.unbindViewModel();
                currentEditor.unbindDiagramViewModel();

                currentEditor.bindDiagramViewModel(diagramViewModel);
                currentEditor.bindViewModel(elementViewModel);
            } else {
                if (currentEditor != null) {
                    currentEditor.unbindViewModel();
                    currentEditor.unbindDiagramViewModel();
                }
                currentEditor = editors.get(elementViewModel.getClass());
                currentEditor.bindDiagramViewModel(diagramViewModel);
                currentEditor.bindViewModel(elementViewModel);
                root.setContent(currentEditor.getRoot());
            }
            currentType = elementViewModel.getClass();
        } else {
            if (currentEditor != null) {
                currentEditor.unbindViewModel();
                currentEditor.unbindDiagramViewModel();
                currentEditor = null;
                currentType = null;
            }
            root.setContent(editors.get(null).getRoot());
        }
    }

}
