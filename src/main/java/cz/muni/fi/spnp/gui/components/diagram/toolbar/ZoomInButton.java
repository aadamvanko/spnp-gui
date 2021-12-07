package cz.muni.fi.spnp.gui.components.diagram.toolbar;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ZoomInButton extends CustomImageButton {
    public ZoomInButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        button.setPrefSize(48, 48);
        button.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("zoom_in_36.png"))));
    }
}
