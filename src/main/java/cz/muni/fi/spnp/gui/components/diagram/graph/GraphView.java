package cz.muni.fi.spnp.gui.components.diagram.graph;

import cz.muni.fi.spnp.gui.components.common.UIComponent;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.ZoomableScrollPane;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.DragPointViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views.ArcView;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.components.diagram.graph.interfaces.Movable;
import cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations.*;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * View representing the graph. The nodes are separated in 3 layers to mimick the Z axis and allow proper rendering af all the elements.
 * The graph elements provide the nodes, which are added to adequate layers.
 */
public class GraphView implements UIComponent {

    // controls
    private final Group layerBottom;
    private final Group layerMiddle;
    private final Group layerTop;
    private final GridBackgroundPane gridBackgroundPane;
    private final ZoomableScrollPane zoomableScrollPane;
    private final List<GraphElementView> graphElementViews;
    private final Rectangle rectangleSelection;
    private final GraphElementViewFactory graphElementViewFactory;
    private MouseOperation mouseOperation;
    private final Model model;
    private DiagramViewModel diagramViewModel;
    private final ListChangeListener<ElementViewModel> onSelectedChangedListener;

    private Point2D initialMousePosition;

    // change listeners
    private final ListChangeListener<ElementViewModel> onElementsChangedListener;
    private List<GraphElementView> selectedViews;
    private final ChangeListener<? super Number> onZoomLevelChangedListener;
    private final ChangeListener<? super Boolean> onGridSnappingChangedListener;

    public GraphView(Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.graphElementViewFactory = new GraphElementViewFactory(this);

        layerBottom = new Group();
        layerMiddle = new Group();
        layerTop = new Group();
        gridBackgroundPane = new GridBackgroundPane();
        gridBackgroundPane.setMinHeight(150);
        gridBackgroundPane.setMinWidth(300);
        gridBackgroundPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
        gridBackgroundPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        gridBackgroundPane.getChildren().addAll(layerBottom, layerMiddle, layerTop);
        zoomableScrollPane = new ZoomableScrollPane(gridBackgroundPane, this::onLayoutChangedHandler);

        graphElementViews = new ArrayList<>();
        selectedViews = new ArrayList<>();

        rectangleSelection = new Rectangle();
        rectangleSelection.setStroke(Color.PURPLE);
        rectangleSelection.getStrokeDashArray().addAll(5.0);
        rectangleSelection.setFill(Color.TRANSPARENT);
        rectangleSelection.setVisible(true);
        layerTop.getChildren().add(rectangleSelection);

        // register mouse events handlers
        gridBackgroundPane.setOnMousePressed(this::onMousePressed);
        gridBackgroundPane.setOnMouseDragged(this::onMouseDragged);
        gridBackgroundPane.setOnMouseReleased(this::onMouseReleased);

        zoomableScrollPane.getZoomGroup().setOnScroll(this::onScrollZoomHandler);
        zoomableScrollPane.firstRenderingProperty().addListener(this::firstRenderingFinished);

        this.onElementsChangedListener = this::onElementsChangedListener;
        this.onSelectedChangedListener = this::onSelectedChangedListener;
        this.onZoomLevelChangedListener = this::onZoomLevelChangedListener;
        this.onGridSnappingChangedListener = this::onGridSnappingChangedListener;

        bindDiagramViewModel(diagramViewModel);
    }

    private Void onLayoutChangedHandler() {
        zoomableScrollPane.setShouldCallLayoutChangedHandler(false);
        if (diagramViewModel.isGridSnapping()) {
            graphElementViews.forEach(Movable::snapToGrid);
        }
        adjustCanvasSize();
        return null;
    }

    private void firstRenderingFinished(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (diagramViewModel.isGridSnapping()) {
            graphElementViews.forEach(Movable::snapToGrid);
        }
        graphElementViews.forEach(graphElementView -> graphElementView.move(Point2D.ZERO));
        graphElementViews.stream()
                .filter(graphElementView -> graphElementView instanceof ArcView)
                .map(graphElementView -> (ArcView) graphElementView)
                .forEach(arcView -> arcView.getDragPointViews().forEach(dragPointView -> dragPointView.move(Point2D.ZERO)));
        adjustCanvasSize();
    }

    private void onScrollZoomHandler(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            if (scrollEvent.getDeltaY() > 0) {
                diagramViewModel.zoomIn();
            } else {
                diagramViewModel.zoomOut();
            }

            scrollEvent.consume();
        }
    }

    private void onZoomLevelChangedListener(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        zoomableScrollPane.zoomTo(newValue.intValue() / 100.0);
    }

    private void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.graphElementViews.clear();

        this.diagramViewModel = diagramViewModel;
        diagramViewModel.getElements().stream()
                .filter(elementViewModel -> elementViewModel instanceof ConnectableViewModel)
                .forEach(this::addGraphElementView);
        diagramViewModel.getElements().stream()
                .filter(elementViewModel -> elementViewModel instanceof ArcViewModel)
                .forEach(this::addGraphElementView);

        // add change listeners
        diagramViewModel.getElements().addListener(this.onElementsChangedListener);

        diagramViewModel.zoomLevelProperty().addListener(this.onZoomLevelChangedListener);
        onZoomLevelChangedListener(null, null, diagramViewModel.getZoomLevel());

        diagramViewModel.gridSnappingProperty().addListener(this.onGridSnappingChangedListener);
        onGridSnappingChangedListener(null, null, diagramViewModel.isGridSnapping());

        diagramViewModel.getSelected().addListener(this.onSelectedChangedListener);
        selectedViews = graphElementViews.stream()
                .filter(graphElementView -> diagramViewModel.getSelected().contains(graphElementView.getViewModel()))
                .collect(Collectors.toList());

        adjustCanvasSize();
    }

    public void unbindViewModels() {
        diagramViewModel.getElements().removeListener(this.onElementsChangedListener);
        diagramViewModel.getSelected().removeListener(this.onSelectedChangedListener);
        diagramViewModel.zoomLevelProperty().removeListener(this.onZoomLevelChangedListener);
        diagramViewModel.gridSnappingProperty().removeListener(this.onGridSnappingChangedListener);

        diagramViewModel = null;
    }

    public Model getModel() {
        return model;
    }

    private void onElementsChangedListener(ListChangeListener.Change<? extends ElementViewModel> elementsChange) {
        while (elementsChange.next()) {
            for (var removedElementViewModel : elementsChange.getRemoved()) {
                var elementView = findElementViewByModel(removedElementViewModel);
                removeGraphElementView(elementView);
            }
            for (var addedElementViewModel : elementsChange.getAddedSubList()) {
                addGraphElementView(addedElementViewModel);
            }
        }
        zoomableScrollPane.setShouldCallLayoutChangedHandler(true);
    }

    private void onSelectedChangedListener(ListChangeListener.Change<? extends ElementViewModel> selectedChange) {
        while (selectedChange.next()) {
            if (selectedChange.wasRemoved()) {
                selectedViews.removeIf(view -> selectedChange.getRemoved().contains(view.getViewModel()));
            }

            selectedChange.getAddedSubList().forEach(viewModel -> selectedViews.add(findElementViewByModel(viewModel)));
        }
    }

    private void addGraphElementView(ElementViewModel addedElementViewModel) {
        var elementView = graphElementViewFactory.createGraphElementView(addedElementViewModel);
        addToLayerBottom(elementView.getBottomLayerContainer());
        addToLayerMiddle(elementView.getMiddleLayerContainer());
        addToLayerTop(elementView.getTopLayerContainer());
        graphElementViews.add(elementView);
        elementView.addedToParent();
    }

    private void removeGraphElementView(GraphElementView elementView) {
        removeFromLayerBottom(elementView.getBottomLayerContainer());
        removeFromLayerMiddle(elementView.getMiddleLayerContainer());
        removeFromLayerTop(elementView.getTopLayerContainer());
        graphElementViews.remove(elementView);
        elementView.removedFromParent();
        elementView.unbindViewModel();
        elementView.setGraphView(null);

        adjustCanvasSize();
    }

    public GraphElementView findElementViewByModel(ElementViewModel elementViewModel) {
        if (elementViewModel instanceof DragPointViewModel) {
            for (var graphElementView : graphElementViews) {
                if (graphElementView instanceof ArcView) {
                    var arcView = (ArcView) graphElementView;
                    for (var dragPointView : arcView.getDragPointViews()) {
                        if (dragPointView.getViewModel() == elementViewModel) {
                            return dragPointView;
                        }
                    }
                }
            }
        }

        return graphElementViews.stream()
                .filter(elementView -> elementView.getViewModel().equals(elementViewModel))
                .findAny()
                .get();
    }

    private void onGridSnappingChangedListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        gridBackgroundPane.setDotsVisibility(newValue);
        if (newValue) {
            graphElementViews.forEach(Movable::snapToGrid);
        }
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        finishMouseOperation();

        initialMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        if (mouseEvent.getButton() == MouseButton.PRIMARY && model.getCursorMode() == CursorMode.VIEW) {
            if (trySelectingNearestArc(mouseEvent)) {
                return;
            } else {
                mouseOperation = new MouseOperationSelection(this);
            }
        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && isCreateModeConnectable()) {
            mouseOperation = new MouseOperationCreateConnectable(this, model);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (trySelectingNearestArc(mouseEvent)) {
                return;
            } else {
                mouseOperation = new MouseOperationPanning(this);
            }
        } else {
            model.cursorModeProperty().set(CursorMode.VIEW);
        }

        if (mouseOperation == null) {
            return;
        }
        mouseOperation.mousePressedHandler(null, mouseEvent);
    }

    private boolean trySelectingNearestArc(MouseEvent mouseEvent) {
        var mousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        ArcView nearestArc = null;
        Line lineSegment = null;
        double minimalDistance = Double.MAX_VALUE;
        final double LINE_SELECTION_DISTANCE = 7;

        for (var element : graphElementViews) {
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
            if (mouseEvent.isControlDown()) {
                diagramViewModel.select(List.of(nearestArc.getViewModel()));
            } else {
                var enrichedMouseEvent = new MouseEvent(lineSegment, lineSegment, mouseEvent.getEventType(),
                        mouseEvent.getX(), mouseEvent.getY(),
                        mouseEvent.getScreenX(), mouseEvent.getScreenY(),
                        mouseEvent.getButton(), mouseEvent.getClickCount(),
                        mouseEvent.isShiftDown(), mouseEvent.isControlDown(), mouseEvent.isAltDown(), mouseEvent.isMetaDown(),
                        mouseEvent.isPrimaryButtonDown(), mouseEvent.isMiddleButtonDown(), mouseEvent.isSecondaryButtonDown(),
                        mouseEvent.isSynthesized(), mouseEvent.isPopupTrigger(), mouseEvent.isStillSincePress(), null);
                nearestArc.onMousePressedHandler(enrichedMouseEvent);
            }
            return true;
        }

        return false;
    }

    private boolean isCreateModeConnectable() {
        var connectableElementsTypes = Set.of(
                GraphElementType.PLACE,
                GraphElementType.IMMEDIATE_TRANSITION,
                GraphElementType.TIMED_TRANSITION
        );
        return isInCreateMode() && connectableElementsTypes.contains(model.getCreateElementType());
    }

    private boolean isCreateModeArc() {
        var arcElementTypes = Set.of(
                GraphElementType.STANDARD_ARC,
                GraphElementType.INHIBITOR_ARC
        );
        return isInCreateMode() && arcElementTypes.contains(model.getCreateElementType());
    }

    private boolean isInCreateMode() {
        return model.getCursorMode() == CursorMode.CREATE || model.getCursorMode() == CursorMode.CREATE_MULTIPLE;
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        if (mouseOperation == null) {
            return;
        }
        mouseOperation.mouseDraggedHandler(null, mouseEvent);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        if (mouseOperation == null) {
            return;
        }
        mouseOperation.mouseReleasedHandler(null, mouseEvent);

        var finalMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        if (mouseOperation instanceof MouseOperationPanning && equalMousePositions(initialMousePosition, finalMousePosition)) {
            mouseOperation.finish();
            mouseOperation = new MouseOperationContextMenu(this, zoomableScrollPane, finalMousePosition);
            mouseOperation.mouseReleasedHandler(null, mouseEvent);
        }
    }

    private boolean equalMousePositions(Point2D first, Point2D second) {
        int firstX = (int) first.getX();
        int firstY = (int) first.getY();
        int secondX = (int) second.getX();
        int secondY = (int) second.getY();
        return firstX == secondX && firstY == secondY;
    }

    public List<GraphElementView> getSelectedViews() {
        return selectedViews;
    }

    public void graphElementPressed(GraphElementView graphElementView, MouseEvent mouseEvent) {
        finishMouseOperation();

        if (mouseEvent.getButton() == MouseButton.PRIMARY && isCreateModeArc()) {
            mouseOperation = new MouseOperationCreateArc(this, model);
        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && model.getCursorMode() == CursorMode.VIEW) {
            mouseOperation = new MouseOperationMoving(this);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            var mousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
            mouseOperation = new MouseOperationContextMenu(this, graphElementView.getContextMenuNode(), mousePosition);
        }

        if (mouseOperation == null) {
            return;
        }

        mouseOperation.mousePressedHandler(graphElementView, mouseEvent);
    }

    public void graphElementDragged(GraphElementView graphElementView, MouseEvent mouseEvent) {
        if (mouseOperation == null) {
            return;
        }

        mouseOperation.mouseDraggedHandler(graphElementView, mouseEvent);
    }

    public void graphElementReleased(GraphElementView graphElementView, MouseEvent mouseEvent) {
        if (mouseOperation == null) {
            return;
        }

        mouseOperation.mouseReleasedHandler(graphElementView, mouseEvent);
    }

    private void finishMouseOperation() {
        if (mouseOperation == null) {
            return;
        }
        mouseOperation.finish();
        mouseOperation = null;
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public GridBackgroundPane getGridPane() {
        return gridBackgroundPane;
    }

    public ZoomableScrollPane getZoomableScrollPane() {
        return zoomableScrollPane;
    }

    public void addToLayerBottom(Node node) {
        if (node == null) {
            return;
        }

        layerBottom.getChildren().add(node);
    }

    public void removeFromLayerBottom(Node node) {
        if (node == null) {
            return;
        }

        layerBottom.getChildren().remove(node);
    }

    public void addToLayerMiddle(Node node) {
        if (node == null) {
            return;
        }

        layerMiddle.getChildren().add(node);
    }

    public void removeFromLayerMiddle(Node node) {
        if (node == null) {
            return;
        }

        layerMiddle.getChildren().remove(node);
    }

    public void addToLayerTop(Node node) {
        if (node == null) {
            return;
        }

        layerTop.getChildren().add(node);
    }

    public void removeFromLayerTop(Node node) {
        if (node == null) {
            return;
        }

        layerTop.getChildren().remove(node);
    }

    public Rectangle getRectangleSelection() {
        return rectangleSelection;
    }

    public void adjustCanvasSize() {
        double maxX = gridBackgroundPane.getMinWidth();
        double maxY = gridBackgroundPane.getMinHeight();
        for (var element : graphElementViews) {
            List<MouseSelectable> mouseSelectables;
            if (element instanceof ArcView) {
                mouseSelectables = ((ArcView) element).getDragPointViews().stream().collect(Collectors.toList());
            } else {
                mouseSelectables = List.of((MouseSelectable) element);
            }

            for (var mouseSelectable : mouseSelectables) {
                var rightBottom = mouseSelectable.rightBottomCorner();
                maxX = Math.max(maxX, rightBottom.getX());
                maxY = Math.max(maxY, rightBottom.getY());
            }
        }
        gridBackgroundPane.setPrefWidth(maxX + GridBackgroundPane.SPACING_X);
        gridBackgroundPane.setPrefHeight(maxY + GridBackgroundPane.SPACING_Y);
    }

    public List<GraphElementView> getGraphElementViews() {
        return graphElementViews;
    }

    @Override
    public Node getRoot() {
        return zoomableScrollPane;
    }

    public void processLayoutChange() {
        zoomableScrollPane.setShouldCallLayoutChangedHandler(true);
    }

}
