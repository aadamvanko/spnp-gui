package cz.muni.fi.spnp.gui.graph.elements;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

public class ElementPlaceView {

    private static final int RADIUS = 16;

    @FXML
    private final Circle circleShape;

    @FXML
    private final Text tokensCountText;

    @FXML
    private final StackPane circleStack;

    @FXML
    private final Label nameLabel;

    @FXML
    private final VBox containerVBox;

    public ElementPlaceView() {
        circleShape = new Circle();
        circleShape.setCenterX(RADIUS);
        circleShape.setCenterY(RADIUS);
        circleShape.setRadius(RADIUS);
        circleShape.setFill(javafx.scene.paint.Color.WHITE);
        circleShape.setStroke(javafx.scene.paint.Color.BLACK);
        circleShape.setStrokeWidth(2);

        tokensCountText = new Text("3");
        tokensCountText.setBoundsType(TextBoundsType.VISUAL);

        circleStack = new StackPane();
        circleStack.getChildren().add(circleShape);
        circleStack.getChildren().add(tokensCountText);
        circleStack.setMaxSize(0, 0);
        circleStack.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTBLUE, null, null)));

        nameLabel = new javafx.scene.control.Label("name");
//        name.setText("name name name");
        nameLabel.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.AQUA, null, null)));
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setMinWidth(Region.USE_PREF_SIZE);
//        name.setMaxWidth(Double.MAX_VALUE);

        containerVBox = new VBox();
        containerVBox.getChildren().add(circleStack);
        containerVBox.getChildren().add(nameLabel);
        containerVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        containerVBox.setMaxHeight(0);
        containerVBox.setMaxWidth(0);
        containerVBox.setAlignment(Pos.CENTER);
        containerVBox.setFillWidth(false);
    }

    public VBox getRoot() {
        return containerVBox;
    }


}
