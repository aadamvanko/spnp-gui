package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementType;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.InhibitorArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.StandardArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcEnding;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcEndingArrow;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcEndingCircle;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.views.TransitionView;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.Collections;

public class MouseOperationCreateArc extends MouseOperationCreate {

    private final Model model;
    private ConnectableGraphElementView fromElement;
    private ConnectableGraphElementView toElement;
    private Line fakeLine;
    private ArcEnding fakeEnding;

    public MouseOperationCreateArc(GraphView graphView, Model model) {
        super(graphView);
        this.model = model;
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        if (graphElementView instanceof ConnectableGraphElementView) {
            if (model.getCreateElementType() == GraphElementType.STANDARD_ARC ||
                    (model.getCreateElementType() == GraphElementType.INHIBITOR_ARC && graphElementView instanceof PlaceView)) {
                fromElement = (ConnectableGraphElementView) graphElementView;

                fakeLine = new Line();
                fakeLine.setStrokeWidth(ArcView.LINE_WIDTH);
                update(mouseEvent);
                graphView.addToLayerTop(fakeLine);

                if (model.getCreateElementType() == GraphElementType.STANDARD_ARC) {
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
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
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
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        if (fakeLine != null) {
            graphView.removeFromLayerTop(fakeLine);
            graphView.removeFromLayerTop(fakeEnding.getShape());
            fakeLine = null;

            // get toLement
            var screenPoint = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
            var elements = graphView.getGraphElementViews();
            for (var element : elements) {
                if (element instanceof ConnectableGraphElementView && isViableTarget(element)) {
                    var connectable = (ConnectableGraphElementView) element;
                    if (connectable.containsPoint(screenPoint)) {
                        toElement = connectable;
                        break;
                    }
                }
            }

            if (toElement != null) {
                ArcViewModel arcViewModel = null;
                if (model.getCreateElementType() == GraphElementType.STANDARD_ARC) {
                    arcViewModel = new StandardArcViewModel("standardArc", fromElement.getViewModel(), toElement.getViewModel(), Collections.emptyList());
                } else {
                    arcViewModel = new InhibitorArcViewModel("inhibitorArc", fromElement.getViewModel(), toElement.getViewModel(), Collections.emptyList());
                }
                renameIfNeeded(graphView.getDiagramViewModel(), arcViewModel);
                graphView.getDiagramViewModel().getElements().add(arcViewModel);

                if (model.getCursorMode() == CursorMode.CREATE) {
                    model.cursorModeProperty().set(CursorMode.VIEW);
                }
            }
        }
    }

    private boolean isViableTarget(GraphElementView target) {
        if (model.getCreateElementType() == GraphElementType.STANDARD_ARC) {
            return (fromElement instanceof PlaceView && target instanceof TransitionView) ||
                    (fromElement instanceof TransitionView && target instanceof PlaceView);
        } else {
            return target instanceof TransitionView;
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
