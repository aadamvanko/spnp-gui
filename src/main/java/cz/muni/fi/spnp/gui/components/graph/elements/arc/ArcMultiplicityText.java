package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ArcMultiplicityText implements ArcMultiplicityVisual {

    private final Text textMultiplicity;

    public ArcMultiplicityText() {
        textMultiplicity = new Text();
    }

    @Override
    public Node getRoot() {
        return textMultiplicity;
    }

    @Override
    public void update(Line line) {
        var start = new Point2D(line.getStartX(), line.getStartY());
        var end = new Point2D(line.getEndX(), line.getEndY());
        var midpoint = start.midpoint(end);
        var vector = end.subtract(start).normalize();
        var perpendicular = new Point2D(vector.getY(), -vector.getX());
        double OFFSET = 10;
        var pos = midpoint.add(perpendicular.multiply(OFFSET));
        textMultiplicity.setX(pos.getX());
        textMultiplicity.setY(pos.getY());
        textMultiplicity.setX(textMultiplicity.getX() - textMultiplicity.getLayoutBounds().getWidth() / 2);
        textMultiplicity.setY(textMultiplicity.getY() + textMultiplicity.getLayoutBounds().getHeight() / 4);
    }

    @Override
    public void setVisible(boolean visible) {
        textMultiplicity.setVisible(visible);
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
