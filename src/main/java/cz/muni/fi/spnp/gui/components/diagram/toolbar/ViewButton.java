package cz.muni.fi.spnp.gui.components.diagram.toolbar;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Button for switching back to the view mode in the editor.
 */
public class ViewButton extends CustomImageToggleButton {
    public ViewButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        toggleButton.setPrefSize(48, 48);
        toggleButton.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("cursor_36.png"))));
    }
}
