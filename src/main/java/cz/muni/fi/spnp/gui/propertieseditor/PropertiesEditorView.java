package cz.muni.fi.spnp.gui.propertieseditor;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class PropertiesEditorView extends Component {

    private final VBox vbox;
    private final Map<GraphElementType, Component> editors;

    public PropertiesEditorView() {
        vbox = new VBox();
        editors = new HashMap<>();
        editors.put(GraphElementType.Place, new PlaceView());
        EntityViewPlaceholder entityViewPlaceholder = new EntityViewPlaceholder();
        editors.put(GraphElementType.None, entityViewPlaceholder);
        vbox.getChildren().add(entityViewPlaceholder.getRoot());
    }

    public Node getRoot() {
        return vbox;
    }

}
