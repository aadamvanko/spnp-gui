package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import javafx.geometry.Point2D;

/**
 * Offers additional operations for 2D vectors needed for intersection computation.
 */
public final class VectorOperations {

    private VectorOperations() {
    }

    public static double calculateDistance(Point2D lineStart, Point2D lineEnd, Point2D point) {
        var v12 = lineEnd.subtract(lineStart);
        var v13 = point.subtract(lineStart);
        var projectionValue = (v13.dotProduct(v12) / Math.pow(v12.magnitude(), 2));
        if (projectionValue < 0 || projectionValue > 1) {
            return Double.MAX_VALUE;
        }

        var projectedVector = v12.multiply(projectionValue);
        var projectedPoint = lineStart.add(projectedVector);
        var distance = projectedPoint.distance(point);
        return distance;
    }

}
