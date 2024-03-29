package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * Circle ending for the inhibitor arcs.
 */
public class ArcEndingCircle implements ArcEnding {

    public static double RADIUS = 5;

    private final Circle circle;

    public ArcEndingCircle(Line line) {
        circle = new Circle();
        circle.setRadius(RADIUS);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);

        updatePosition(line);
    }

    private void updatePosition(Line line) {
        Point2D start = new Point2D(line.getStartX(), line.getStartY());
        Point2D end = new Point2D(line.getEndX(), line.getEndY());
        Point2D direction = end.subtract(start);
        Point2D position = end.subtract(direction.normalize().multiply(circle.getRadius() + circle.getStrokeWidth() / 2));
        circle.setTranslateX(position.getX());
        circle.setTranslateY(position.getY());
    }

    @Override
    public Shape getShape() {
        return circle;
    }

    @Override
    public void update(Line line) {
        updatePosition(line);
    }

//    public void move(Point2D offset) {
//        circle.setTranslateX(circle.getTranslateX() + offset.getX());
//        circle.setTranslateY(circle.getTranslateY() + offset.getY());
//    }
}
