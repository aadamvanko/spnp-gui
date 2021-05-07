package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.*;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.components.graph.elements.transition.TransitionController;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.InhibitorArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.StandardArcViewModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MouseOperationCreateArc extends MouseOperation {

    private final GraphElementType createElementType;
    private ConnectableGraphElement fromElement;
    private ConnectableGraphElement toElement;
    private Line fakeLine;
    private ArcEnding fakeEnding;

    public MouseOperationCreateArc(GraphView graphView) {
        super(graphView);
        createElementType = graphView.getCreateElementType();
    }

    @Override
    public void mousePressedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        if (graphElement instanceof ConnectableGraphElement) {
            if (createElementType == GraphElementType.STANDARD_ARC ||
                    (createElementType == GraphElementType.INHIBITOR_ARC && graphElement instanceof PlaceController)) {
                fromElement = (ConnectableGraphElement) graphElement;

                fakeLine = new Line();
                fakeLine.setStrokeWidth(ArcController.LINE_WIDTH);
                update(mouseEvent);
                graphView.addToLayerTop(fakeLine);

                if (createElementType == GraphElementType.STANDARD_ARC) {
                    fakeEnding = new ArcEndingArrow(fakeLine);
                } else {
                    fakeEnding = new ArcEndingCircle(fakeLine);
                }
                updateFakeEnding();
                graphView.addToLayerTop(fakeEnding.getShape());
            }
        }
    }

    @Override
    public void mouseDraggedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        if (fakeLine != null) {
            update(mouseEvent);
            updateFakeEnding();
        }
    }

    private void update(MouseEvent mouseEvent) {
        var localGridPoint = graphView.getGridPane().screenToLocal(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        localGridPoint = preventInvalidCoordinates(localGridPoint);
        var borderPoint = fromElement.getBorderConnectionPoint(localGridPoint);
//            var borderPoint = fromElement.getBorderConnectionPoint(localGridPoint);
        fakeLine.setStartX(borderPoint.getX());
        fakeLine.setStartY(borderPoint.getY());
        fakeLine.setEndX(localGridPoint.getX());
        fakeLine.setEndY(localGridPoint.getY());
    }

    private Point2D preventInvalidCoordinates(Point2D point) {
        double x = Math.max(point.getX(), GridBackgroundPane.SPACING_X);
        double y = Math.max(point.getY(), GridBackgroundPane.SPACING_Y);
        x = Math.min(x, graphView.getGridPane().getWidth());
        y = Math.min(y, graphView.getGridPane().getHeight());
        return new Point2D(x, y);
    }

    private void updateFakeEnding() {
        if (fakeEnding instanceof ArcEndingArrow) {
            fakeEnding.update(fakeLine);
            var start = new Point2D(fakeLine.getStartX(), fakeLine.getStartY());
            var end = new Point2D(fakeLine.getEndX(), fakeLine.getEndY());
            var direction = end.subtract(start);
            var newEnd = end.subtract(direction.normalize().multiply(ArcEndingArrow.LENGTH * 0.75));
            fakeLine.setEndX(newEnd.getX());
            fakeLine.setEndY(newEnd.getY());

        } else {
            fakeEnding.getShape().setTranslateX(fakeLine.getEndX());
            fakeEnding.getShape().setTranslateY(fakeLine.getEndY());
        }
    }

    @Override
    public void mouseReleasedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        if (fakeLine != null) {
            graphView.removeFromLayerTop(fakeLine);
            graphView.removeFromLayerTop(fakeEnding.getShape());
            fakeLine = null;

            // get toLement
            var screenPoint = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
            var elements = graphView.getElements();
            for (var element : elements) {
                if (element instanceof ConnectableGraphElement && isViableTarget(element)) {
                    var connectable = (ConnectableGraphElement) element;
                    if (connectable.containsPoint(screenPoint)) {
                        toElement = connectable;
                        break;
                    }
                }
            }

            if (toElement != null) {
                ArcViewModel arcViewModel = null;
                if (createElementType == GraphElementType.STANDARD_ARC) {
                    arcViewModel = new StandardArcViewModel("standardArc", fromElement.getViewModel(), toElement.getViewModel(), Collections.emptyList());
                } else {
                    arcViewModel = new InhibitorArcViewModel("inhibitorArc", fromElement.getViewModel(), toElement.getViewModel(), Collections.emptyList());
                }
                graphView.getDiagramViewModel().addElement(arcViewModel);

                if (graphView.getCursorMode() == CursorMode.CREATE) {
                    graphView.setCursorMode(CursorMode.VIEW);
                }
            }
        }
    }

    private boolean isViableTarget(GraphElement target) {
        if (createElementType == GraphElementType.STANDARD_ARC) {
            return (fromElement instanceof PlaceController && target instanceof TransitionController) ||
                    (fromElement instanceof TransitionController && target instanceof PlaceController);
        } else {
            return target instanceof TransitionController;
        }
    }

    @Override
    public void finish() {
        if (fakeLine != null) {
            graphView.removeFromLayerTop(fakeLine);
            graphView.removeFromLayerTop(fakeEnding.getShape());
            fakeLine = null;
        }
    }
}
