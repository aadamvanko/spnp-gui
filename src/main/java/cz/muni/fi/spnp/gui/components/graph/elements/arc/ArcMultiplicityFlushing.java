package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

public class ArcMultiplicityFlushing implements ArcMultiplicityVisual {

    private final Polyline polyline;
    double OFFSET_DIRECTION = 5;
    double OFFSET_PERPENDICULAR = 10;

    public ArcMultiplicityFlushing() {
        polyline = new Polyline();
        polyline.setStroke(Color.RED);
        polyline.setStrokeWidth(1);
        polyline.setSmooth(true);
    }

    public void updateVertices(Line line) {
        Point2D lineStart = new Point2D(line.getStartX(), line.getStartY());
        Point2D lineEnd = new Point2D(line.getEndX(), line.getEndY());
        Point2D direction = lineEnd.subtract(lineStart);
        Point2D perpendicular = new Point2D(-direction.getY(), direction.getX());

        Point2D midpoint = lineStart.midpoint(lineEnd);
        Point2D directionOffset = direction.normalize().multiply(OFFSET_DIRECTION);
        Point2D perpendicularOffset = perpendicular.normalize().multiply(OFFSET_PERPENDICULAR);
        Point2D first = midpoint.subtract(directionOffset).add(perpendicularOffset);
        Point2D second = midpoint.subtract(directionOffset).subtract(perpendicularOffset);
        Point2D third = midpoint.add(directionOffset).add(perpendicularOffset);
        Point2D fourth = midpoint.add(directionOffset).subtract(perpendicularOffset);

        double[] vertices = {
                first.getX(), first.getY(),
                second.getX(), second.getY(),
                third.getX(), third.getY(),
                fourth.getX(), fourth.getY(),
        };

        var points = polyline.getPoints();
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
    public void update(Line line) {
        updateVertices(line);
    }

    @Override
    public void setVisible(boolean visible) {
        polyline.setVisible(visible);
    }

    @Override
    public void bindViewModel(ArcViewModel arcViewModel) {
    }

    @Override
    public void unbindViewModel(ArcViewModel unbindViewModel) {
    }

    @Override
    public Node getRoot() {
        return polyline;
    }

}
