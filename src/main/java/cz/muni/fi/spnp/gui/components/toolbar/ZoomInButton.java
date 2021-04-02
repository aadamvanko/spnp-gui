package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ZoomInButton extends CustomImageToggleButton {
    public ZoomInButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        toggleButton.setPrefSize(48, 48);
        toggleButton.setText("+");
    }
}
