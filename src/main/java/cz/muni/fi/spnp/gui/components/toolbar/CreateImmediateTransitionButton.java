package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CreateImmediateTransitionButton extends CustomImageButton {
    public CreateImmediateTransitionButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(30);
        rectangle.setWidth(9);
        rectangle.setFill(Color.BLACK);

        button.setPrefSize(48, 48);
        button.setGraphic(rectangle);
    }
}
