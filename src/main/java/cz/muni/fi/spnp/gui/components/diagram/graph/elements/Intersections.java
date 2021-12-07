package cz.muni.fi.spnp.gui.components.diagram.graph.elements;

import javafx.geometry.Point2D;

/**
 * Utility class for calculation of the intersection point of two vectors.
 */
public final class Intersections {

    private Intersections() {
    }

    private static double signedTriangleArea(Point2D a, Point2D b, Point2D c) {
        return (a.getX() - c.getX()) * (b.getY() - c.getY()) - (a.getY() - c.getY()) * (b.getX() - c.getX());
    }

    public static Point2D segmentsIntersect(Point2D a, Point2D b, Point2D c, Point2D d) {
        double a1 = signedTriangleArea(a, b, d);
        double a2 = signedTriangleArea(a, b, c);

        if (a1 * a2 < 0.0f) {
            double a3 = signedTriangleArea(c, d, a);
            double a4 = a3 + a2 - a1;
            if (a3 * a4 < 0.0f) {
                double t = a3 / (a3 - a4);
                Point2D p = a.add((b.subtract(a).multiply(t)));
                return p;
            }
        }
        return null;
    }
}
