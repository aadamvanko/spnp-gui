package cz.muni.fi.spnp.gui.components.diagram.toolbar;

import cz.muni.fi.spnp.gui.components.common.UIComponent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Base class for button with an image.
 */
public abstract class CustomImageButton implements UIComponent {
    protected Button button;

    public CustomImageButton(EventHandler<MouseEvent> onClickHandler) {
        button = new Button();
        button.setOnMouseClicked(onClickHandler);
    }

    @Override
    public Button getRoot() {
        return button;
    }
}
