package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.graph.canvas.ZoomableScrollPane;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.DragPointView;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.components.graph.interfaces.Movable;
import cz.muni.fi.spnp.gui.components.graph.mouseoperations.*;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationCopyElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationCutElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationPasteElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationSelectAll;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphView {

    private final Group layerBottom;
    private final Group layerMiddle;
    private final Group layerTop;
    private final GridBackgroundPane gridBackgroundPane;
    private final ZoomableScrollPane zoomableScrollPane;
    private final List<GraphElementView> graphElementViews;
    private final Rectangle rectangleSelection;
    private final GraphElementViewFactory graphElementViewFactory;
    private MouseOperation mouseOperation;
    private final Notifications notifications;
    private final Model model;
    private DiagramViewModel diagramViewModel;
    private List<GraphElementView> selected;

    private Point2D initialMousePosition;

    private final ListChangeListener<ElementViewModel> onElementsChangedListener;
    private final ChangeListener<? super Number> onZoomLevelChangedListener;
    private final ChangeListener<? super Boolean> onGridSnappingChangedListener;

    public GraphView(Notifications notifications, Model model, DiagramViewModel diagramViewModel) {
        this.notifications = notifications;
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
        zoomableScrollPane = new ZoomableScrollPane(gridBackgroundPane);

        graphElementViews = new ArrayList<>();
        selected = new ArrayList<>();

        rectangleSelection = new Rectangle();
//        rectangleSelection.setWidth(100);
//        rectangleSelection.setHeight(50);
        rectangleSelection.setStroke(Color.PURPLE);
//        rectangleSelection.setStrokeWidth(1);
        rectangleSelection.getStrokeDashArray().addAll(5.0);
        rectangleSelection.setFill(Color.TRANSPARENT);
//        rectangleSelection.setFill(Color.color(0.8, 0.0, 1.0, 0.05));
        rectangleSelection.setVisible(true);
        layerTop.getChildren().add(rectangleSelection);

        gridBackgroundPane.setOnMousePressed(this::onMousePressed);
        gridBackgroundPane.setOnMouseDragged(this::onMouseDragged);
        gridBackgroundPane.setOnMouseReleased(this::onMouseReleased);

        zoomableScrollPane.setOnKeyReleased(this::onKeyReleased);
        zoomableScrollPane.getZoomGroup().setOnScroll(this::onScrollZoomHandler);

        adjustCanvasSize();


        this.onElementsChangedListener = this::onElementsChangedListener;
        this.onZoomLevelChangedListener = this::onZoomLevelChangedListener;
        this.onGridSnappingChangedListener = this::onGridSnappingChangedListener;

        bindDiagramViewModel(diagramViewModel);

        model.gridSnappingProperty().addListener(this.onGridSnappingChangedListener);
        onGridSnappingChangedListener(null, null, model.isGridSnapping());
    }

    private void onScrollZoomHandler(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            if (scrollEvent.getDeltaY() < 0) {
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
        resetSelection();
        this.graphElementViews.clear();

        this.diagramViewModel = diagramViewModel;
        diagramViewModel.getElements().stream()
                .filter(elementViewModel -> elementViewModel instanceof ConnectableViewModel)
                .forEach(this::addGraphElementView);
        diagramViewModel.getElements().stream()
                .filter(elementViewModel -> elementViewModel instanceof ArcViewModel)
                .forEach(this::addGraphElementView);

        diagramViewModel.getElements().addListener(this.onElementsChangedListener);
        diagramViewModel.zoomLevelProperty().addListener(this.onZoomLevelChangedListener);
    }

    public void unbindViewModels() {
        diagramViewModel.getElements().removeListener(this.onElementsChangedListener);
        diagramViewModel.zoomLevelProperty().removeListener(this.onZoomLevelChangedListener);

        model.gridSnappingProperty().removeListener(this.onGridSnappingChangedListener);

        this.diagramViewModel = null;
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()) {
            if (keyEvent.getCode() == KeyCode.C && !selected.isEmpty()) {
                new OperationCopyElements(this).execute();
            } else if (keyEvent.getCode() == KeyCode.X && !selected.isEmpty()) {
                new OperationCutElements(this).execute();
            } else if (keyEvent.getCode() == KeyCode.V && !model.getClipboardElements().isEmpty()) {
                new OperationPasteElements(this).execute();
            } else if (keyEvent.getCode() == KeyCode.A) {
                new OperationSelectAll(this).execute();
            }
        }
    }

    public Model getModel() {
        return model;
    }

    private void onElementsChangedListener(ListChangeListener.Change<? extends ElementViewModel> elementsChange) {
        while (elementsChange.next()) {
            for (var addedElementViewModel : elementsChange.getAddedSubList()) {
                addGraphElementView(addedElementViewModel);
            }
            for (var removedElementViewModel : elementsChange.getRemoved()) {
                var elementView = findElementViewByModel(removedElementViewModel);
                removeGraphElementView(elementView);
            }
        }
    }

    private void addGraphElementView(ElementViewModel addedElementViewModel) {
        var elementView = graphElementViewFactory.createGraphElementView(addedElementViewModel);
        addToLayerBottom(elementView.getBottomLayerContainer());
        addToLayerMiddle(elementView.getMiddleLayerContainer());
        addToLayerTop(elementView.getTopLayerContainer());
        graphElementViews.add(elementView);
        elementView.addedToParent();

        adjustCanvasSize();
    }

    private void removeGraphElementView(GraphElementView elementView) {
        removeFromLayerBottom(elementView.getBottomLayerContainer());
        removeFromLayerMiddle(elementView.getMiddleLayerContainer());
        removeFromLayerTop(elementView.getTopLayerContainer());
        graphElementViews.remove(elementView);
        elementView.unbindViewModel();
        elementView.setGraphView(null);
        elementView.removedFromParent();

        adjustCanvasSize();
    }

    public GraphElementView findElementViewByModel(ElementViewModel elementViewModel) {
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
        System.out.println("canvas mouse pressed");
        finishMouseOperation();

        initialMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());
        if (mouseEvent.getButton() == MouseButton.PRIMARY && model.getCursorMode() == CursorMode.VIEW) {
            mouseOperation = new MouseOperationSelection(this);
        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && isCreateModeConnectable()) {
            mouseOperation = new MouseOperationCreateConnectable(this, model);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            mouseOperation = new MouseOperationPanning(this);
        } else {
            model.cursorModeProperty().set(CursorMode.VIEW);
        }

        if (mouseOperation == null) {
            return;
        }
        mouseOperation.mousePressedHandler(null, mouseEvent);
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
//        System.out.println("canvas mouse dragged");
        if (mouseOperation == null) {
            return;
        }
        mouseOperation.mouseDraggedHandler(null, mouseEvent);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        System.out.println("canvas mouse released");

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

    public void moveSelected(Point2D moveOffset) {
        if (selected.stream().allMatch(element -> element.canMove(moveOffset))) {
            selected.forEach(element -> element.move(moveOffset));
        }
    }

    public void moveSelectedEnded() {
        if (model.isGridSnapping()) {
            selected.forEach(Movable::snapToGrid);
        }
    }

    public List<GraphElementView> getSelected() {
        return selected;
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

    public void selectViewModels(List<ElementViewModel> selectedViewModels) {
        resetSelection();
        var selectedViews = selectedViewModels.stream().map(this::findElementViewByModel).collect(Collectors.toList());
        var selectedDragPoints = selectedViews.stream()
                .filter(graphElementView -> graphElementView instanceof ArcView)
                .map(graphElementView -> ((ArcView) graphElementView).getDragPointViews())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        selectedViews.addAll(selectedDragPoints);
        selectedViews.forEach(GraphElementView::enableHighlight);
        select(selectedViews);
    }

    public void select(GraphElementView graphElementView) {
        resetSelection();
        graphElementView.enableHighlight();
        selected.add(graphElementView);
        fireSelectedElementsChanged();
    }

    public void select(List<GraphElementView> selectedElements) {
        resetSelection();
        this.selected = selectedElements;
        fireSelectedElementsChanged();
    }

    private void fireSelectedElementsChanged() {
        if (selected.size() == 1 && selected.get(0) instanceof DragPointView) {
            return;
        }
        notifications.selectedElementsChanged(selected);
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public void resetSelection() {
        selected.forEach(GraphElementView::disableHighlight);
        selected.clear();
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
            if (!(element instanceof MouseSelectable)) {
                continue;
            }
            MouseSelectable mouseSelectable = (MouseSelectable) element;
            var rightBottom = mouseSelectable.rightBottomCorner();
//            System.out.println(rightBottom);
            maxX = Math.max(maxX, rightBottom.getX());
            maxY = Math.max(maxY, rightBottom.getY());
        }
        gridBackgroundPane.setPrefWidth(maxX + GridBackgroundPane.SPACING_X);
        gridBackgroundPane.setPrefHeight(maxY + GridBackgroundPane.SPACING_Y);
    }

    public List<GraphElementView> getGraphElementViews() {
        return graphElementViews;
    }

}
