package cz.muni.fi.spnp.gui.graph.elements.place;

import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.graph.elements.arc.ArcController;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

public class PlaceController extends ConnectableGraphElement {

    private Circle circle;

    private Text tokensCountText;
    private StackPane circleStack;
    private Label nameLabel;
    private VBox container;

    public PlaceController(double x, double y) {
        createView(x, y);
        createBindings();
    }

    private void createView(double x, double y) {
        circle = new Circle();
        circle.setRadius(20);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        circle.setSmooth(true);
        circle.setStrokeType(StrokeType.OUTSIDE);
        registerMouseHandlers(circle);

        tokensCountText = new Text("3");
        tokensCountText.setBoundsType(TextBoundsType.VISUAL);
        tokensCountText.setSmooth(true);
        registerMouseHandlers(tokensCountText);

        circleStack = new StackPane();
        circleStack.getChildren().add(circle);
        circleStack.getChildren().add(tokensCountText);
        circleStack.setMaxSize(0, 0);

        nameLabel = new Label("name");
//        name.setText("name name name");
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setMinWidth(Region.USE_PREF_SIZE);
//        name.setMaxWidth(Double.MAX_VALUE);

        container = new VBox();
        container.getChildren().add(circleStack);
        container.getChildren().add(nameLabel);
        container.setMaxHeight(0);
        container.setMaxWidth(0);
        container.setAlignment(Pos.CENTER);
        container.setFillWidth(false);
        container.setTranslateX(x);
        container.setTranslateY(y);

        if (false) {
            circleStack.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
            nameLabel.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
            container.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        }
    }

    private void createBindings() {
    }

    @Override
    public Point2D getShapeCenter() {
        double x = circle.getLayoutX() + circleStack.getTranslateX() + container.getTranslateX();
        double y = circle.getLayoutY() + circleStack.getTranslateY() + container.getTranslateY();
        return new Point2D(x, y);
    }

    @Override
    public Point2D rightBottomCorner() {
        return new Point2D(container.getBoundsInParent().getMaxX(), container.getBoundsInParent().getMaxY());
    }

    @Override
    public Point2D getBorderConnectionPoint(Point2D from) {
        Point2D center = getShapeCenter();
        Point2D direction = center.subtract(from);
        double moveBackDistance = circle.getRadius() + circle.getStrokeWidth() + ArcController.LINE_WIDTH / 2.0;
        Point2D intersection = center.subtract(direction.normalize().multiply(moveBackDistance));
        return intersection;
    }

    public Circle getCircle() {
        return circle;
    }

    public VBox getRoot() {
        return container;
    }

    @Override
    public void enableHighlight() {
        super.enableHighlight();
        circle.setEffect(highlightEffect);
    }

    @Override
    public void disableHighlight() {
        super.disableHighlight();
        circle.setEffect(null);
    }

    @Override
    public void addToParent(GraphView parent) {
        super.addToParent(parent);
        parent.addToLayerMiddle(container);
    }

    @Override
    public void removeFromParent(GraphView parent) {
        super.removeFromParent(parent);
        parent.removeFromLayerMiddle(container);
    }

    @Override
    public void snapToGrid() {
        snapToGrid(getShapeCenter(), getContainerPosition());
        updateArcs();
    }

    private Point2D getContainerPosition() {
        return new Point2D(container.getTranslateX(), container.getTranslateY());
    }

    @Override
    public void move(Point2D moveOffset) {
        moveViaTranslate(container, moveOffset);
        updateArcs();
    }

    @Override
    public boolean canMove(Point2D offset) {
        return canMove(getShapeCenter(), getContainerPosition(), offset);
    }

    @Override
    public Node getContextMenuNode() {
        return circle;
    }

    @Override
    public boolean containsPoint(Point2D screenPoint) {
        var localPoint = circle.screenToLocal(screenPoint);
        return circle.contains(localPoint);
    }
}
