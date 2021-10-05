package cz.muni.fi.spnp.gui.components.graph.elements.transition;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.Intersections;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionOrientation;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class TransitionView extends ConnectableGraphElementView {

    private Label nameLabel;
    private final ChangeListener<String> onNameChangedListener;
    protected Label probabilityTypeLabel;
    protected Rectangle rectangle;
    private Group container;

    private final ChangeListener<FunctionViewModel> onGuardFunctionChangedListener;
    private final ChangeListener<TransitionOrientation> onOrientationChangedListener;
    private Label guardFunctionLabel;


    public TransitionView(GraphView graphView, TransitionViewModel transitionViewModel) {
        super(graphView, transitionViewModel);

        this.onOrientationChangedListener = this::onOrientationChangedListener;
        this.onGuardFunctionChangedListener = this::onGuardFunctionChangedListener;
        this.onNameChangedListener = this::onNameChangedListener;

        createView();
    }

    private void onNameChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        executeDelayedUpdate(() -> Platform.runLater(() -> updateNameLabelPosition()));
    }

    private void onOrientationChangedListener(ObservableValue<? extends TransitionOrientation> observableValue, TransitionOrientation oldValue, TransitionOrientation newValue) {
        if (newValue == TransitionOrientation.Vertical) {
            rectangle.setHeight(getRectangleDefaultHeight());
            rectangle.setWidth(getRectangleDefaultWidth());
        } else {
            rectangle.setHeight(getRectangleDefaultWidth());
            rectangle.setWidth(getRectangleDefaultHeight());
        }
        updateArcs();
        graphView.processLayoutChange();
    }

    private void onGuardFunctionChangedListener(ObservableValue<? extends FunctionViewModel> observableValue, FunctionViewModel oldValue, FunctionViewModel newValue) {
        var functionName = newValue == null ? "null" : newValue.getName();
        guardFunctionLabel.setText(String.format("[%s]", functionName));
        updateArcs();
        executeDelayedUpdate(() -> Platform.runLater(() -> updateGuardLabelPosition()));
    }

    private void updateAllComponents() {
        updateGuardLabelPosition();
        updateProbabilityTypeLabelPosition();
        updateNameLabelPosition();
    }

    private void updateGuardLabelPosition() {
        guardFunctionLabel.setLayoutX(rectangle.getLayoutX() - guardFunctionLabel.getWidth());
        guardFunctionLabel.setLayoutY(rectangle.getLayoutY() - guardFunctionLabel.getHeight());
    }

    private void updateProbabilityTypeLabelPosition() {
        probabilityTypeLabel.setLayoutX(rectangle.getLayoutX() + rectangle.getWidth());
        probabilityTypeLabel.setLayoutY(rectangle.getLayoutY() - probabilityTypeLabel.getHeight());
    }

    private void updateNameLabelPosition() {
        nameLabel.setLayoutX(rectangle.getLayoutX() - nameLabel.getWidth());
        nameLabel.setLayoutY(rectangle.getLayoutY() + rectangle.getHeight());
    }

    protected abstract double getRectangleDefaultWidth();

    protected abstract double getRectangleDefaultHeight();

    @Override
    public TransitionViewModel getViewModel() {
        return (TransitionViewModel) viewModel;
    }

    private void createView() {
        rectangle = new Rectangle();

        guardFunctionLabel = new Label("guard");
        guardFunctionLabel.setMinWidth(Region.USE_PREF_SIZE);
        guardFunctionLabel.setMouseTransparent(true);
        guardFunctionLabel.setStyle("-fx-font-style: italic");

        probabilityTypeLabel = new Label("type");
        probabilityTypeLabel.setMinWidth(Region.USE_PREF_SIZE);
        probabilityTypeLabel.setMouseTransparent(true);
        probabilityTypeLabel.setStyle("-fx-font-style: italic");

        nameLabel = new Label("name");
        nameLabel.setMinWidth(Region.USE_PREF_SIZE);
        nameLabel.setMouseTransparent(true);

        container = new Group(guardFunctionLabel, rectangle, probabilityTypeLabel, nameLabel);

        if (false) {
            nameLabel.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
//            container.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        }
    }

    @Override
    protected void bindViewModel() {
        super.bindViewModel();

        nameLabel.textProperty().bind(viewModel.nameProperty());
        getViewModel().nameProperty().addListener(this.onNameChangedListener);
        // TODO priority, guard function

        container.translateXProperty().bind(getViewModel().positionXProperty());
        container.translateYProperty().bind(getViewModel().positionYProperty());

        getViewModel().orientationProperty().addListener(this.onOrientationChangedListener);
        onOrientationChangedListener(null, null, getViewModel().getOrientation());

        getViewModel().guardFunctionProperty().addListener(this.onGuardFunctionChangedListener);
        onGuardFunctionChangedListener(null, null, getViewModel().getGuardFunction());
    }

    @Override
    public void unbindViewModel() {
        nameLabel.textProperty().unbind();
        getViewModel().nameProperty().removeListener(this.onNameChangedListener);

        container.translateXProperty().unbind();
        container.translateYProperty().unbind();

        getViewModel().orientationProperty().removeListener(this.onOrientationChangedListener);
        getViewModel().guardFunctionProperty().removeListener(this.onGuardFunctionChangedListener);

        super.unbindViewModel();
    }

    public Group getRoot() {
        return container;
    }

    @Override
    public Point2D getBorderConnectionPoint(Point2D from) {
        Point2D center = getShapeCenter();
        var corners = getRectangleCorners();
        for (int i = 0; i < corners.length; i++) {
            int next = (i + 1) % corners.length;
            Point2D intersection = Intersections.segmentsIntersect(from, center, corners[i], corners[next]);
            if (intersection != null) {
                return intersection;
            }
        }
        return closestPoint(from, corners);
    }

    private Point2D closestPoint(Point2D point, Point2D[] points) {
        if (points.length == 0) {
            return null;
        }
        Point2D closest = points[0];
        double closestDistance = point.distance(closest);
        for (int i = 1; i < points.length; i++) {
            double distance = point.distance(points[i]);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = points[i];
            }
        }
        return closest;
    }

    private Point2D[] getRectangleCorners() {
        Point2D topLeftCorner = new Point2D(container.getTranslateX() + rectangle.getLayoutX(),
                container.getTranslateY() + rectangle.getLayoutY());
        Point2D[] corners = new Point2D[4];
        corners[0] = topLeftCorner;
        corners[1] = topLeftCorner.add(new Point2D(rectangle.getWidth(), 0));
        corners[2] = topLeftCorner.add(new Point2D(rectangle.getWidth(), rectangle.getHeight()));
        corners[3] = topLeftCorner.add(new Point2D(0, rectangle.getHeight()));
        return corners;
    }

    @Override
    public Point2D getShapeCenter() {
        double x = container.getTranslateX() + rectangle.getLayoutX() + rectangle.getWidth() / 2 - 1;
        double y = container.getTranslateY() + rectangle.getLayoutY() + rectangle.getHeight() / 2 - 1;
        return new Point2D(x, y);
    }

    @Override
    public Point2D rightBottomCorner() {
        return new Point2D(container.getBoundsInParent().getMaxX(), container.getBoundsInParent().getMaxY());
    }

    @Override
    protected void enableHighlight() {
        rectangle.setEffect(highlightEffect);
    }

    @Override
    protected void disableHighlight() {
        rectangle.setEffect(null);
    }

    @Override
    public Node getBottomLayerContainer() {
        return null;
    }

    @Override
    public Node getMiddleLayerContainer() {
        return container;
    }

    @Override
    public Node getTopLayerContainer() {
        return null;
    }

    @Override
    public void addedToParent() {
        registerMouseHandlers(rectangle);
    }

    @Override
    public void removedFromParent() {
        unregisterMouseHandlers(rectangle);
    }

    @Override
    public void snapToGrid() {
        snapToGrid(getShapeCenter(), getContainerPosition());
        updateArcs();
    }

    @Override
    protected Point2D getContainerPosition() {
        return new Point2D(container.getTranslateX(), container.getTranslateY());
    }

    @Override
    public void move(Point2D moveOffset) {
        moveViaTranslate(moveOffset);
        updateArcs();
        updateAllComponents();
    }

    @Override
    public boolean canMove(Point2D offset) {
        return canMove(getShapeCenter(), getContainerPosition(), offset);
    }

    @Override
    public Node getContextMenuNode() {
        return rectangle;
    }

    @Override
    public boolean containsPoint(Point2D screenPoint) {
        var localPoint = rectangle.screenToLocal(screenPoint);
        return rectangle.contains(localPoint);
    }
}
