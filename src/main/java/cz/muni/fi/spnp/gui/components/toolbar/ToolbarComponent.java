package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ToolbarComponent extends ApplicationComponent {

    private final HBox hbox;

    public ToolbarComponent(Model model) {
        super(model);

        hbox = new HBox();

        hbox.getChildren().add(new Button("cursor"));
        hbox.getChildren().add(new Button("mover"));
    }

    @Override
    public Node getRoot() {
        return hbox;
    }
}
