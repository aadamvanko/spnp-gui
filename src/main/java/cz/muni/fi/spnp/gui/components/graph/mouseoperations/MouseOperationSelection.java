package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.DragPointView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class MouseOperationSelection extends MouseOperation {

    private final Rectangle rectangleSelection;

    public MouseOperationSelection(GraphView graphView) {
        super(graphView);
        rectangleSelection = graphView.getRectangleSelection();

        System.out.println("selection operation created");
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        graphView.resetSelection();

        rectangleSelection.setWidth(0);
        rectangleSelection.setHeight(0);
        rectangleSelection.setVisible(true);
        rectangleSelection.setTranslateX(mouseEvent.getX());
        rectangleSelection.setTranslateY(mouseEvent.getY());
//        System.out.println(rectangleSelection.getTranslateX() + " - " + rectangleSelection.getTranslateY());
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        GridBackgroundPane gridBackgroundPane = graphView.getGridPane();
        var localPoint = gridBackgroundPane.screenToLocal(mouseEvent.getScreenX(), mouseEvent.getScreenY());

        if (gridBackgroundPane.contains(localPoint.getX() + 1, 0)) {
            rectangleSelection.setWidth(mouseEvent.getX() - rectangleSelection.getTranslateX());
        } else {
            rectangleSelection.setWidth(gridBackgroundPane.getWidth() - rectangleSelection.getTranslateX() - 1);
        }

        if (gridBackgroundPane.contains(0, localPoint.getY() + 1)) {
            rectangleSelection.setHeight(mouseEvent.getY() - rectangleSelection.getTranslateY());
        } else {
            rectangleSelection.setHeight(gridBackgroundPane.getHeight() - rectangleSelection.getTranslateY() - 1);
        }
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        rectangleSelection.setVisible(false);

        graphView.resetSelection();
        List<GraphElementView> selected = new ArrayList<>();
        for (var element : graphView.getGraphElementViews()) {
            if (element instanceof MouseSelectable) {
                MouseSelectable mouseSelectable = (MouseSelectable) element;
//            System.out.println(mouseSelectable);
//            System.out.println(mouseSelectable.getShapeCenter());
                if (rectangleSelection.getBoundsInParent().contains(mouseSelectable.getShapeCenter())) {
                    element.enableHighlight();
                    selected.add(element);
                }
            } else if (element instanceof ArcView) {
                var arcView = (ArcView) element;
                for (var dragPointView : arcView.getDragPointViews()) {
                    if (rectangleSelection.getBoundsInParent().contains(dragPointView.getShapeCenter())) {
                        dragPointView.enableHighlight();
                        selected.add(dragPointView);
                    }
                }
                if (!arcView.getDragPointViews().isEmpty() && arcView.getDragPointViews().stream().allMatch(DragPointView::isHighlighted)) {
                    arcView.enableHighlight();
                    selected.add(arcView);
                } else if (arcView.getDragPointViews().isEmpty() && arcView.hasHighlightedEnds()) {
                    arcView.enableHighlight();
                    selected.add(arcView);
                }
            }
        }

        graphView.select(selected);
    }

    @Override
    public void finish() {
        rectangleSelection.setVisible(false);
    }
}
