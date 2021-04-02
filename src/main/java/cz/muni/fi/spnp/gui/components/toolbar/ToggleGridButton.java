package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ToggleGridButton extends CustomImageToggleButton {
    public ToggleGridButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        toggleButton.setPrefSize(48, 48);
        toggleButton.setText("Grid");
    }
}
