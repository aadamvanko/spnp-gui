package cz.muni.fi.spnp.gui.components.diagram.toolbar;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class CreatePlaceButton extends CustomImageToggleButton {

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

        toggleButton.setPrefSize(48, 48);
        toggleButton.setGraphic(circle);
    }
}
