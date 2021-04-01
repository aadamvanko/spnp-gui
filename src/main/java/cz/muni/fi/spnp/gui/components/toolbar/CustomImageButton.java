package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.UIComponent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public abstract class CustomImageButton implements UIComponent {

    protected Button button;

    public CustomImageButton(EventHandler<MouseEvent> onClickHandler) {
        button = new Button();
        button.setOnMouseClicked(onClickHandler);
    }

    @Override
    public Node getRoot() {
        return button;
    }
}
