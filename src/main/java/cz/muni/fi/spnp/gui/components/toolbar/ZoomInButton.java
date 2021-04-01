package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ZoomInButton extends CustomImageButton {
    public ZoomInButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        button.setPrefSize(48, 48);
        button.setText("+");
    }
}
