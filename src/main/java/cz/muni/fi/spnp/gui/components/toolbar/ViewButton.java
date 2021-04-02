package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ViewButton extends CustomImageToggleButton {
    public ViewButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        toggleButton.setText("View");
        toggleButton.setPrefSize(48, 48);
    }
}
