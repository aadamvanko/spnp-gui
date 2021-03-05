package cz.muni.fi.spnp.gui.toolbar;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ToolbarView extends Component {

    private final HBox hbox;

    public ToolbarView() {
        hbox = new HBox();

        hbox.getChildren().add(new Button("cursor"));
        hbox.getChildren().add(new Button("mover"));
    }

    @Override
    public Node getRoot() {
        return hbox;
    }
}
