package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcController;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionController;
import cz.muni.fi.spnp.gui.components.propertieseditor.arc.InhibitorArcPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.arc.StandardArcPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.place.PlacePropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.transition.ImmediateTransitionPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.transition.TimedTransitionPropertiesEditor;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.notifications.SelectedElementsChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesComponent extends ApplicationComponent implements SelectedElementsChangeListener {

    private final VBox vbox;
    private final Map<Class<?>, PropertiesEditor> editors;
    private Class<?> currentType;
    private PropertiesEditor currentEditor;

    public PropertiesComponent(Model model, Notifications notifications) {
        super(model, notifications);

        editors = new HashMap<>();
        editors.put(null, new PropertiesEditorPlaceholder());
        editors.put(PlaceController.class, new PlacePropertiesEditor());
        editors.put(StandardArcController.class, new StandardArcPropertiesEditor());
        editors.put(InhibitorArcController.class, new InhibitorArcPropertiesEditor());
        editors.put(ImmediateTransitionController.class, new ImmediateTransitionPropertiesEditor());
        editors.put(TimedTransitionController.class, new TimedTransitionPropertiesEditor());

        vbox = new VBox();
        vbox.getChildren().add(editors.get(null).getRoot());

        notifications.addSelectedElementsChangeListener(this);
    }

    public Node getRoot() {
        return vbox;
    }

    @Override
    public void onSelectedElementsChanged(List<GraphElement> selectedElements) {
        if (selectedElements.size() == 1) {
            var element = selectedElements.get(0);
            if (element.getClass().equals(currentType)) {
                currentEditor.unbindViewModel();
                currentEditor.bindViewModel(element.getViewModel());
            } else {
                if (currentEditor != null) {
                    currentEditor.unbindViewModel();
                }
                currentEditor = editors.get(element.getClass());
                currentEditor.bindViewModel(element.getViewModel());
                vbox.getChildren().set(0, currentEditor.getRoot());
            }
            currentType = element.getClass();
        } else {
            if (currentEditor != null) {
                currentEditor.unbindViewModel();
                currentEditor = null;
                currentType = null;
            }
        }
    }
}
