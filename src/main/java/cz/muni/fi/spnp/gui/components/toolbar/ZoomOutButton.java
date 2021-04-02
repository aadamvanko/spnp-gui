package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ZoomOutButton extends CustomImageToggleButton {
    public ZoomOutButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        toggleButton.setPrefSize(48, 48);
        toggleButton.setText("-");
        //button.setGraphic(null);
    }
}
