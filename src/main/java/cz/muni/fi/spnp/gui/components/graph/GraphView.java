package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.components.graph.canvas.ZoomableScrollPane;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcDragMark;
import cz.muni.fi.spnp.gui.components.graph.interfaces.MouseSelectable;
import cz.muni.fi.spnp.gui.components.graph.mouseoperations.*;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ConnectableElementViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GraphView {

    private final Group layerBottom;
    private final Group layerMiddle;
    private final Group layerTop;
    private final GridBackgroundPane gridBackgroundPane;
    private final ZoomableScrollPane zoomableScrollPane;
    private final List<GraphElement> elements;
    private final Rectangle rectangleSelection;
    private List<GraphElement> selected;
    private CursorMode cursorMode;
    private GraphElementType createElementType;
    private MouseOperation mouseOperation;
    private boolean snappingToGrid;
    private final Notifications notifications;
    private DiagramViewModel diagramViewModel;

    public GraphView(Notifications notifications) {
        this.notifications = notifications;

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

        elements = new ArrayList<>();
        selected = new ArrayList<>();

        cursorMode = CursorMode.VIEW;
        createElementType = GraphElementType.STANDARD_ARC;

        rectangleSelection = new Rectangle();
//        rectangleSelection.setWidth(100);
//        rectangleSelection.setHeight(50);
        rectangleSelection.setStroke(Color.PURPLE);
//        rectangleSelection.setStrokeWidth(1);
        rectangleSelection.getStrokeDashArray().addAll(5.0);
        rectangleSelection.setFill(Color.TRANSPARENT);
//        rectangleSelection.setFill(Color.color(0.8, 0.0, 1.0, 0.05));
        rectangleSelection.setVisible(true);
        addToLayerTop(rectangleSelection);

        gridBackgroundPane.setOnMousePressed(this::onMousePressed);
        gridBackgroundPane.setOnMouseDragged(this::onMouseDragged);
        gridBackgroundPane.setOnMouseReleased(this::onMouseReleased);

        setSnappingToGrid(true);
        adjustCanvasSize();
    }

    public void select(List<GraphElement> selectedElements) {
        resetSelection();
        this.selected = selectedElements;
        fireSelectedElementsChanged();
    }

    public void setCursorMode(CursorMode cursorMode) {
        this.cursorMode = cursorMode;
    }

    public void setSnappingToGrid(boolean snappingToGrid) {
        gridBackgroundPane.setDotsVisibility(snappingToGrid);
        if (!this.snappingToGrid && snappingToGrid) {
            elements.forEach(element -> element.snapToGrid());
        }
        this.snappingToGrid = snappingToGrid;
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        System.out.println("canvas mouse pressed");
        finishMouseOperation();

        if (mouseEvent.getButton() == MouseButton.PRIMARY && cursorMode == CursorMode.VIEW) {
            mouseOperation = new MouseOperationSelection(this);
        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && isInCreateMode()) {
            mouseOperation = new MouseOperationCreate(this);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            mouseOperation = new MouseOperationPanning(this);
        }

        if (mouseOperation == null) {
            return;
        }
        mouseOperation.mousePressedHandler(null, mouseEvent);
    }

    private boolean isInCreateMode() {
        return cursorMode == CursorMode.CREATE || cursorMode == CursorMode.CREATE_MULTIPLE;
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
    }

    public void moveSelected(Point2D moveOffset) {
        if (selected.stream().allMatch(element -> element.canMove(moveOffset))) {
            selected.forEach(element -> element.move(moveOffset));
        }
    }

    public void moveSelectedEnded() {
        if (snappingToGrid) {
            selected.forEach(element -> element.snapToGrid());
        }
    }

    public List<GraphElement> getSelected() {
        return selected;
    }

    public void graphElementPressed(GraphElement graphElement, MouseEvent mouseEvent) {
        finishMouseOperation();

        if (mouseEvent.getButton() == MouseButton.PRIMARY && isInCreateMode()) {
            mouseOperation = new MouseOperationCreateArc(this);
        } else if (mouseEvent.getButton() == MouseButton.PRIMARY && cursorMode == CursorMode.VIEW) {
            mouseOperation = new MouseOperationMoving(this);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            mouseOperation = new MouseOperationContextMenu(this);
        }

        if (mouseOperation == null) {
            return;
        }

        mouseOperation.mousePressedHandler(graphElement, mouseEvent);
    }

    public void graphElementDragged(GraphElement graphElement, MouseEvent mouseEvent) {
        if (mouseOperation == null) {
            return;
        }

        mouseOperation.mouseDraggedHandler(graphElement, mouseEvent);
    }

    public void graphElementReleased(GraphElement graphElement, MouseEvent mouseEvent) {
        if (mouseOperation == null) {
            return;
        }

        mouseOperation.mouseReleasedHandler(graphElement, mouseEvent);
    }

    private void finishMouseOperation() {
        if (mouseOperation == null) {
            return;
        }
        mouseOperation.finish();
        mouseOperation = null;
    }

    public void select(GraphElement graphElement) {
        resetSelection();
        graphElement.enableHighlight();
        selected.add(graphElement);
        fireSelectedElementsChanged();
    }

    private void fireSelectedElementsChanged() {
        if (selected.size() == 1 && selected.get(0) instanceof ArcDragMark) {
            return;
        }
        notifications.selectedElementsChanged(selected);
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public void resetSelection() {
        selected.forEach(element -> element.disableHighlight());
        selected.clear();
    }

    public void addElement(GraphElement graphElement) {
        elements.add(graphElement);
        adjustCanvasSize();
    }

    public void removeElement(GraphElement graphElement) {
        elements.remove(graphElement);
        adjustCanvasSize();
    }

    public GridBackgroundPane getGridPane() {
        return gridBackgroundPane;
    }

    public ZoomableScrollPane getZoomableScrollPane() {
        return zoomableScrollPane;
    }

    public void addToLayerBottom(Node node) {
        layerBottom.getChildren().add(node);
    }

    public void removeFromLayerBottom(Node node) {
        layerBottom.getChildren().remove(node);
    }

    public void addToLayerMiddle(Node node) {
        layerMiddle.getChildren().add(node);
    }

    public void removeFromLayerMiddle(Node node) {
        layerMiddle.getChildren().remove(node);
    }

    public void addToLayerTop(Node node) {
        layerTop.getChildren().add(node);
    }

    public void removeFromLayerTop(Node node) {
        layerTop.getChildren().remove(node);
    }

    public boolean isSnappingEnabled() {
        return snappingToGrid;
    }

    public Rectangle getRectangleSelection() {
        return rectangleSelection;
    }

    public void adjustCanvasSize() {
        double maxX = gridBackgroundPane.getMinWidth();
        double maxY = gridBackgroundPane.getMinHeight();
        for (var element : elements) {
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

    public GraphElementType getCreateElementType() {
        return createElementType;
    }

    public void setCreateElementType(GraphElementType createElementType) {
        this.createElementType = createElementType;
    }

    public List<GraphElement> getElements() {
        return elements;
    }

    public CursorMode getCursorMode() {
        return cursorMode;
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        resetSelection();
        this.elements.clear();

        this.diagramViewModel = diagramViewModel;
        var graphElementFactory = new GraphElementFactory(this);
        diagramViewModel.getElements().stream()
                .filter(elementViewModel -> elementViewModel instanceof ConnectableElementViewModel)
                .forEach(elementViewModel -> graphElementFactory.createGraphElement(elementViewModel));
        diagramViewModel.getElements().stream()
                .filter(elementViewModel -> elementViewModel instanceof ArcViewModel)
                .forEach(elementViewModel -> graphElementFactory.createGraphElement(elementViewModel));
    }
}
