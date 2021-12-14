package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.DragPointViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.DragPointView;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.MouseSelectable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mouse operation for selecting elements in the graph via the rectangular area.
 */
public class MouseOperationSelection extends MouseOperation {

    private final Rectangle rectangleSelection;

    public MouseOperationSelection(GraphView graphView) {
        super(graphView);
        rectangleSelection = graphView.getRectangleSelection();
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        graphView.getDiagramViewModel().resetSelection();

        rectangleSelection.setWidth(0);
        rectangleSelection.setHeight(0);
        rectangleSelection.setVisible(true);
        rectangleSelection.setTranslateX(mouseEvent.getX());
        rectangleSelection.setTranslateY(mouseEvent.getY());

        // try selecting the nearest arc
        var mousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        ArcView nearestArc = null;
        Line lineSegment = null;
        double minimalDistance = Double.MAX_VALUE;
        final double LINE_SELECTION_DISTANCE = 7;

        for (var element : graphView.getGraphElementViews()) {
            if (element instanceof ArcView) {
                var arcView = (ArcView) element;
                var allPoints = arcView.getAllPoints();
                for (int i = 0; i < allPoints.size() - 1; i++) {
                    var distance = VectorOperations.calculateDistance(allPoints.get(i), allPoints.get(i + 1), mousePosition);
                    if (distance <= LINE_SELECTION_DISTANCE) {
                        if (nearestArc == null || distance < minimalDistance) {
                            nearestArc = arcView;
                            minimalDistance = distance;
                            lineSegment = arcView.getLines().get(i);
                        }
                    }
                }
            }
        }

        if (nearestArc != null) {
            var enrichedMouseEvent = new MouseEvent(lineSegment, lineSegment, mouseEvent.getEventType(),
                    mouseEvent.getX(), mouseEvent.getY(),
                    mouseEvent.getScreenX(), mouseEvent.getScreenY(),
                    mouseEvent.getButton(), mouseEvent.getClickCount(),
                    mouseEvent.isShiftDown(), mouseEvent.isControlDown(), mouseEvent.isAltDown(), mouseEvent.isMetaDown(),
                    mouseEvent.isPrimaryButtonDown(), mouseEvent.isMiddleButtonDown(), mouseEvent.isSecondaryButtonDown(),
                    mouseEvent.isSynthesized(), mouseEvent.isPopupTrigger(), mouseEvent.isStillSincePress(), null);
            nearestArc.onMousePressedHandler(enrichedMouseEvent);
        }
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

        graphView.getDiagramViewModel().resetSelection();
        List<GraphElementView> selected = new ArrayList<>();
        for (var element : graphView.getGraphElementViews()) {
            if (element instanceof MouseSelectable) {
                MouseSelectable mouseSelectable = (MouseSelectable) element;
                if (rectangleSelection.getBoundsInParent().contains(mouseSelectable.getShapeCenter())) {
                    element.getViewModel().highlightedProperty().set(true);
                    selected.add(element);
                }
            } else if (element instanceof ArcView) {
                var arcView = (ArcView) element;
                for (var dragPointView : arcView.getDragPointViews()) {
                    if (rectangleSelection.getBoundsInParent().contains(dragPointView.getShapeCenter())) {
                        dragPointView.getViewModel().highlightedProperty().set(true);
                        selected.add(dragPointView);
                    }
                }
                if (!arcView.getDragPointViews().isEmpty() && areAllDragPointsHighlighted(arcView)) {
                    arcView.getViewModel().highlightedProperty().set(true);
                    selected.add(arcView);
                } else if (arcView.getDragPointViews().isEmpty() && arcView.hasHighlightedEnds()) {
                    arcView.getViewModel().highlightedProperty().set(true);
                    selected.add(arcView);
                }
            }
        }

        var selectedViewModels = selected.stream().map(GraphElementView::getViewModel).collect(Collectors.toList());
        graphView.getDiagramViewModel().select(selectedViewModels);
    }

    private boolean areAllDragPointsHighlighted(ArcView arcView) {
        return arcView.getDragPointViews().stream()
                .map(DragPointView::getViewModel)
                .allMatch(DragPointViewModel::isHighlighted);
    }

    @Override
    public void finish() {
        rectangleSelection.setVisible(false);
    }
}
