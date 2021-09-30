package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ArcMultiplicityText implements ArcMultiplicityVisual {

    private static final double TEXT_OFFSET = 15;
    private static final double CROSS_LINE_LENGTH = 8;
    private static final double CROSS_LINE_ROTATION_OFFSET = 4;

    private final Group container;
    private final Line crossLine;
    private final Text textMultiplicity;

    public ArcMultiplicityText() {
        crossLine = new Line();
        crossLine.setStrokeWidth(1);

        textMultiplicity = new Text();

        container = new Group(crossLine, textMultiplicity);
    }

    @Override
    public Node getRoot() {
        return container;
    }

    @Override
    public void update(Line line) {
        var start = new Point2D(line.getStartX(), line.getStartY());
        var end = new Point2D(line.getEndX(), line.getEndY());
        var midpoint = start.midpoint(end);
        var vector = end.subtract(start).normalize();
        var perpendicular = new Point2D(vector.getY(), -vector.getX());
        var pos = midpoint.add(perpendicular.multiply(TEXT_OFFSET));
        textMultiplicity.setX(pos.getX());
        textMultiplicity.setY(pos.getY());
        textMultiplicity.setX(textMultiplicity.getX() - textMultiplicity.getLayoutBounds().getWidth() / 2);
        textMultiplicity.setY(textMultiplicity.getY() + textMultiplicity.getLayoutBounds().getHeight() / 4);

        var perpendicularNormalized = perpendicular.normalize();
        var lineStart = midpoint.subtract(perpendicularNormalized.multiply(CROSS_LINE_LENGTH));
        lineStart = lineStart.add(vector.multiply(CROSS_LINE_ROTATION_OFFSET));
        var lineEnd = midpoint.add(perpendicularNormalized.multiply(CROSS_LINE_LENGTH));
        lineEnd = lineEnd.subtract(vector.multiply(CROSS_LINE_ROTATION_OFFSET));
        crossLine.setStartX(lineStart.getX());
        crossLine.setStartY(lineStart.getY());
        crossLine.setEndX(lineEnd.getX());
        crossLine.setEndY(lineEnd.getY());
    }

    @Override
    public void setVisible(boolean visible) {
        container.setVisible(visible);
    }

    @Override
    public void bindViewModel(ArcViewModel arcViewModel) {
        textMultiplicity.textProperty().bind(arcViewModel.multiplicityProperty());
    }

    @Override
    public void unbindViewModel(ArcViewModel unbindViewModel) {
        textMultiplicity.textProperty().unbind();
    }

}
