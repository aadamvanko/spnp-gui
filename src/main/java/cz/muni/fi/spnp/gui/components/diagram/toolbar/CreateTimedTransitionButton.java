package cz.muni.fi.spnp.gui.components.diagram.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Button for creating timed transitions.
 */
public class CreateTimedTransitionButton extends CustomImageToggleButton {
    public CreateTimedTransitionButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeType(StrokeType.OUTSIDE);
        rectangle.setStrokeWidth(1);
        rectangle.setWidth(15);
        rectangle.setHeight(30);
        rectangle.setLayoutX(2);
        rectangle.setLayoutY(2);

        toggleButton.setPrefSize(48, 48);

        toggleButton.setGraphic(rectangle);
    }
}
