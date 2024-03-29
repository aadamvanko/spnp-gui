package cz.muni.fi.spnp.gui.components.diagram.toolbar;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Button for toggling grid functionality.
 */
public class ToggleGridButton extends CustomImageToggleButton {
    public ToggleGridButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        toggleButton.setPrefSize(48, 48);
        toggleButton.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("grid_36.png"))));
    }
}
