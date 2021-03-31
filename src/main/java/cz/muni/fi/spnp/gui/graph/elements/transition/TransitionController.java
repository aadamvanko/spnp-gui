package cz.muni.fi.spnp.gui.graph.elements.transition;

import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.graph.elements.Intersections;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;


public abstract class TransitionController extends ConnectableGraphElement {

    private final Label nameLabel;
    private final VBox container;
    protected Rectangle rectangle;

    public TransitionController(double x, double y) {
        rectangle = new Rectangle();
        registerMouseHandlers(rectangle);

        nameLabel = new Label("transition");
        nameLabel.setText("name name name");
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setMinWidth(Region.USE_PREF_SIZE);
//        name.setMaxWidth(Double.MAX_VALUE);

        container = new VBox();
        container.getChildren().add(rectangle);
        container.getChildren().add(nameLabel);
        container.setMaxHeight(0);
        container.setMaxWidth(0);
        container.setAlignment(Pos.CENTER);
        container.setFillWidth(false);
        container.setTranslateX(x);
        container.setTranslateY(y);

        if (false) {
            nameLabel.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
            container.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        }
    }

    public VBox getRoot() {
        return container;
    }

    @Override
    public Point2D getBorderConnectionPoint(Point2D from) {
        Point2D center = getShapeCenter();
        var corners = getRectangleCorners();
        for (int i = 0; i < corners.length; i++) {
            int next = (i + 1) % corners.length;
            Point2D intersection = Intersections.segmentsIntersect(from, center, corners[i], corners[next]);
            if (intersection != null) {
                return intersection;
            }
        }
        return closestPoint(from, corners);
    }

    private Point2D closestPoint(Point2D point, Point2D[] points) {
        if (points.length == 0) {
            return null;
        }
        Point2D closest = points[0];
        double closestDistance = point.distance(closest);
        for (int i = 1; i < points.length; i++) {
            double distance = point.distance(points[i]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = points[i];
            }
        }
        return closest;
    }

    private Point2D[] getRectangleCorners() {
        Point2D topLeftCorner = new Point2D(container.getTranslateX() + rectangle.getLayoutX(), container.getTranslateY() + rectangle.getLayoutY());
        Point2D[] corners = new Point2D[4];
        corners[0] = topLeftCorner;
        corners[1] = topLeftCorner.add(new Point2D(rectangle.getWidth(), 0));
        corners[2] = topLeftCorner.add(new Point2D(rectangle.getWidth(), rectangle.getHeight()));
        corners[3] = topLeftCorner.add(new Point2D(0, rectangle.getHeight()));
        return corners;
    }

    @Override
    public Point2D getShapeCenter() {
        double x = container.getTranslateX() + rectangle.getLayoutX() + rectangle.getWidth() / 2 - 1;
        double y = container.getTranslateY() + rectangle.getLayoutY() + rectangle.getHeight() / 2 - 1;
        return new Point2D(x, y);
    }

    @Override
    public Point2D rightBottomCorner() {
        return new Point2D(container.getBoundsInParent().getMaxX(), container.getBoundsInParent().getMaxY());
    }

    @Override
    public void enableHighlight() {
        super.enableHighlight();
        rectangle.setEffect(highlightEffect);
    }

    @Override
    public void disableHighlight() {
        super.disableHighlight();
        rectangle.setEffect(null);
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
        return rectangle;
    }

    @Override
    public boolean containsPoint(Point2D screenPoint) {
        var localPoint = rectangle.screenToLocal(screenPoint);
        return rectangle.contains(localPoint);
    }
}
