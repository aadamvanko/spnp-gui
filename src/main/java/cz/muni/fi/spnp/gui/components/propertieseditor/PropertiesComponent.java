package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.InhibitorArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.StandardArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.ImmediateTransitionView;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TimedTransitionView;
import cz.muni.fi.spnp.gui.components.propertieseditor.arc.InhibitorArcPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.arc.StandardArcPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.place.PlacePropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.transition.ImmediateTransitionPropertiesEditor;
import cz.muni.fi.spnp.gui.components.propertieseditor.transition.TimedTransitionPropertiesEditor;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.notifications.SelectedElementsChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertiesComponent extends ApplicationComponent implements SelectedElementsChangeListener {

    private final VBox vbox;
    private final Map<Class<?>, PropertiesEditor> editors;
    private Class<?> currentType;
    private PropertiesEditor currentEditor;

    public PropertiesComponent(Model model, Notifications notifications) {
        super(model);

        editors = new HashMap<>();
        editors.put(null, new PropertiesEditorPlaceholder());
        editors.put(PlaceView.class, new PlacePropertiesEditor());
        editors.put(StandardArcView.class, new StandardArcPropertiesEditor());
        editors.put(InhibitorArcView.class, new InhibitorArcPropertiesEditor());
        editors.put(ImmediateTransitionView.class, new ImmediateTransitionPropertiesEditor());
        editors.put(TimedTransitionView.class, new TimedTransitionPropertiesEditor());

        vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vbox.getChildren().add(editors.get(null).getRoot());

        notifications.addSelectedElementsChangeListener(this);
    }

    public Node getRoot() {
        return vbox;
    }

    @Override
    public void onSelectedElementsChanged(List<GraphElementView> selectedElements) {
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
