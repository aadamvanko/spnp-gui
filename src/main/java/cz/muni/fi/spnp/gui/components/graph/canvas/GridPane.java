package cz.muni.fi.spnp.gui.components.graph.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GridPane extends Pane {
    public static final int SPACING_X = 20;
    public static final int SPACING_Y = 20;
    private final Canvas canvas = new Canvas();

    public GridPane() {
        getChildren().add(canvas);
    }

    public void setDotsVisibility(boolean visibility) {
        canvas.setVisible(visibility);
    }

    @Override
    protected void layoutChildren() {
        final int top = (int) snappedTopInset();
        final int right = (int) snappedRightInset();
        final int bottom = (int) snappedBottomInset();
        final int left = (int) snappedLeftInset();
        final int w = (int) getWidth() - left - right;
        final int h = (int) getHeight() - top - bottom;
        final int size = 1;
        canvas.setLayoutX(left);
        canvas.setLayoutY(top);
        if (w != canvas.getWidth() || h != canvas.getHeight()) {
            canvas.setWidth(w);
            canvas.setHeight(h);
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.clearRect(0, 0, w, h);
            g.setFill(Color.WHITE);
            g.fillRect(0, 0, w, h);
            g.setFill(Color.gray(0.5, 1));

            for (int x = 0; x < w; x += SPACING_X) {
                for (int y = 0; y < h; y += SPACING_Y) {
//                    double offsetY = (y%(2*SPACING_Y)) == 0 ? SPACING_X /2 : 0;
                    double offsetY = 0;
                    g.fillRect(x, y, size, size);
                }
            }
        }
    }
}
