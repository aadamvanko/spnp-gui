package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ToggleGridButton extends CustomImageButton {
    public ToggleGridButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        button.setPrefSize(48, 48);
        button.setText("Grid");
    }
}
