package cz.muni.fi.spnp.gui.components.graph.elements.place;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

public class PlaceView extends ConnectableGraphElementView {

    public static final double RADIUS = 20;

    private Circle circle;
    private final ChangeListener<? super String> onTokensCountChangedListener;
    private Label nameLabel;
    private Label tokensCountLabel;
    private Group container;
    private final ChangeListener<? super String> onNameChangedListener;

    public PlaceView(GraphView graphView, PlaceViewModel placeViewModel) {
        super(graphView, placeViewModel);

        this.onTokensCountChangedListener = this::onTokensCountChangedListener;
        this.onNameChangedListener = this::onNameChangedListener;

        createView();
        bindViewModel();
    }

    private void onNameChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        executeDelayedUpdate(() -> Platform.runLater(() -> updateNameLabelPosition()));
    }

    private void onTokensCountChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        executeDelayedUpdate(() -> Platform.runLater(() -> updateTokensCountLabelPosition()));
    }

    private void updateNameLabelPosition() {
        nameLabel.setLayoutX(circle.getLayoutX() - circle.getRadius() - nameLabel.getWidth());
        nameLabel.setLayoutY(circle.getLayoutY() + circle.getRadius());
    }

    private void updateTokensCountLabelPosition() {
        tokensCountLabel.setLayoutX(circle.getCenterX() - tokensCountLabel.getWidth() / 2.0);
        tokensCountLabel.setLayoutY(circle.getCenterY() - tokensCountLabel.getHeight() / 2.0);
    }

    @Override
    public PlaceViewModel getViewModel() {
        return (PlaceViewModel) viewModel;
    }

    private void createView() {
        circle = new Circle();
        circle.setRadius(RADIUS);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        circle.setSmooth(true);
        circle.setStrokeType(StrokeType.OUTSIDE);

        tokensCountLabel = new Label("3");
        tokensCountLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            tokensCountLabel.setVisible(!newValue.equals("0"));
        });
        tokensCountLabel.setMouseTransparent(true);

        nameLabel = new Label("name");
        nameLabel.setMinWidth(Region.USE_PREF_SIZE);
        nameLabel.setMouseTransparent(true);

        container = new Group(circle, tokensCountLabel, nameLabel);

        if (false) {
            nameLabel.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
        }
    }

    @Override
    protected void bindViewModel() {
        super.bindViewModel();

        nameLabel.textProperty().bind(getViewModel().nameProperty());
        getViewModel().nameProperty().addListener(this.onNameChangedListener);

        tokensCountLabel.textProperty().bind(getViewModel().numberOfTokensProperty());
        getViewModel().numberOfTokensProperty().addListener(this.onTokensCountChangedListener);

        container.translateXProperty().bind(getViewModel().positionXProperty());
        container.translateYProperty().bind(getViewModel().positionYProperty());
    }

    @Override
    public void unbindViewModel() {
        nameLabel.textProperty().unbind();
        getViewModel().nameProperty().removeListener(this.onNameChangedListener);

        tokensCountLabel.textProperty().unbind();
        getViewModel().numberOfTokensProperty().removeListener(this.onTokensCountChangedListener);

        container.translateXProperty().unbind();
        container.translateYProperty().unbind();

        super.unbindViewModel();
    }

    @Override
    public Point2D getShapeCenter() {
        double x = container.getTranslateX() + circle.getLayoutX();
        double y = container.getTranslateY() + circle.getLayoutY();
        return new Point2D(x, y);
    }

    @Override
    public Point2D rightBottomCorner() {
        return new Point2D(container.getBoundsInParent().getMaxX(), container.getBoundsInParent().getMaxY());
    }

    @Override
    public Point2D getBorderConnectionPoint(Point2D from) {
        Point2D center = getShapeCenter();
        Point2D direction = center.subtract(from);
        double moveBackDistance = circle.getRadius() + circle.getStrokeWidth() + ArcView.LINE_WIDTH / 2.0;
        Point2D intersection = center.subtract(direction.normalize().multiply(moveBackDistance));
        return intersection;
    }

    public Circle getCircle() {
        return circle;
    }

    public Group getRoot() {
        return container;
    }

    @Override
    protected void enableHighlight() {
        circle.setEffect(highlightEffect);
    }

    @Override
    protected void disableHighlight() {
        circle.setEffect(null);
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
        registerMouseHandlers(circle);
        registerMouseHandlers(tokensCountLabel);
    }

    @Override
    public void removedFromParent() {
        unregisterMouseHandlers(circle);
        unregisterMouseHandlers(tokensCountLabel);
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
        updateAllPositions();
    }

    private void updateAllPositions() {
        updateTokensCountLabelPosition();
        updateNameLabelPosition();
    }

    @Override
    public boolean canMove(Point2D offset) {
        return canMove(getShapeCenter(), getContainerPosition(), offset);
    }

    @Override
    public Node getContextMenuNode() {
        return circle;
    }

    @Override
    public boolean containsPoint(Point2D screenPoint) {
        var localPoint = circle.screenToLocal(screenPoint);
        return circle.contains(localPoint);
    }
}
