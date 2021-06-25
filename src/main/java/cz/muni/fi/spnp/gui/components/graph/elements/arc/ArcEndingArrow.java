package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class ArcEndingArrow implements ArcEnding {

    public static double LENGTH = 10;
    public static double BASE_WIDTH = 5;

    private final Polygon polygon;

    public ArcEndingArrow(Line line) {
        polygon = new Polygon();
        polygon.setFill(Color.WHITE);
//        setStroke(Color.INDIANRED);
        polygon.setStroke(Color.BLACK);
//        setStrokeWidth(1);
        polygon.setRotationAxis(new Point3D(0, 0, 1));
        polygon.setSmooth(true);

        updateVertices(line);
    }

    public void updateVertices(Line line) {
        Point2D lineStart = new Point2D(line.getStartX(), line.getStartY());
        Point2D lineEnd = new Point2D(line.getEndX(), line.getEndY());
        Point2D direction = lineEnd.subtract(lineStart);
        Point2D arrowBase = lineEnd.subtract(direction.normalize().multiply(LENGTH));
        Point2D perpendicular = new Point2D(-direction.getY(), direction.getX());
        Point2D sideVector = perpendicular.normalize().multiply(BASE_WIDTH);
        Point2D corner1 = arrowBase.add(sideVector);
        Point2D corner2 = arrowBase.subtract(sideVector);

        double[] vertices = {lineEnd.getX(), lineEnd.getY(), corner1.getX(), corner1.getY(), corner2.getX(), corner2.getY()};
        var points = polygon.getPoints();
        if (points.size() == 0) {
            for (double vertexComponent : vertices) {
                points.add(vertexComponent);
            }
        } else {
            for (int i = 0; i < points.size(); i++) {
                points.set(i, vertices[i]);
            }
        }
    }

    @Override
    public Shape getShape() {
        return polygon;
    }

    @Override
    public void update(Line line) {
        updateVertices(line);
    }

//    public void move(Point2D offset) {
//        for (int i = 0; i < polygon.getPoints().size(); i += 2) {
//            double x = polygon.getPoints().get(i);
//            double y = polygon.getPoints().get(i + 1);
//            polygon.getPoints().set(i, x + offset.getX());
//            polygon.getPoints().set(i + 1, y + offset.getY());
//        }
//    }
}
