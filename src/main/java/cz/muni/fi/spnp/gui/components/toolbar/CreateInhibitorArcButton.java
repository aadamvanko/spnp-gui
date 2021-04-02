package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcEndingCircle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class CreateInhibitorArcButton extends CustomImageToggleButton {
    public CreateInhibitorArcButton(EventHandler<MouseEvent> onClickHandler) {
        super(onClickHandler);

        Pane pane = new Pane();

        Line line = new Line(3, 35, 33, 5);
        ArcEndingCircle circleEnding = new ArcEndingCircle(line);
        line.setEndX(line.getEndX() - 3);
        line.setEndY(line.getEndY() + 3);

        pane.getChildren().add(line);
        pane.getChildren().add(circleEnding.getShape());

        toggleButton.setPrefSize(48, 48);
        toggleButton.setGraphic(pane);
    }
}
