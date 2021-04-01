package cz.muni.fi.spnp.gui.components.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CreatePlaceButton extends CustomImageButton {

    public CreatePlaceButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        Circle circle = new Circle();
        circle.setRadius(15);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStrokeWidth(1);
        circle.setLayoutX(1);
        circle.setLayoutY(16);

        button.setPrefSize(48, 48);
        button.setGraphic(circle);
    }
}
