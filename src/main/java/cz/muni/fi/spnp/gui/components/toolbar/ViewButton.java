package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ViewButton extends CustomImageButton {
    public ViewButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        button.setText("View");
        button.setPrefSize(48, 48);
    }
}
