package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class PropertiesComponent extends ApplicationComponent {

    private final VBox vbox;
    private final Map<GraphElementType, PropertiesEditor> editors;

    public PropertiesComponent(Model model, Notifications notifications) {
        super(model, notifications);

        vbox = new VBox();
        editors = new HashMap<>();
        editors.put(GraphElementType.Place, new PlacePropertiesEditor());
        ElementPropertiesEditorPlaceholder elementPropertiesEditorPlaceholder = new ElementPropertiesEditorPlaceholder();
        editors.put(GraphElementType.None, elementPropertiesEditorPlaceholder);
        vbox.getChildren().add(elementPropertiesEditorPlaceholder.getRoot());
    }

    public Node getRoot() {
        return vbox;
    }

}
