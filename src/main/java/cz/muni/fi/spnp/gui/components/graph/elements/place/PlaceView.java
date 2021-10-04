package cz.muni.fi.spnp.gui.components.graph.elements.place;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.graph.elements.arc.ArcView;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

public class PlaceView extends ConnectableGraphElementView {

    private Circle circle;
    private Text tokensCountText;
    private StackPane circleStack;
    private Label nameLabel;
    private VBox container;

    private final ChangeListener<? super String> onTokensChangedListener;
    private final ChangeListener<? super String> onNameChangedListener;

    public PlaceView(GraphView graphView, PlaceViewModel placeViewModel) {
        super(graphView, placeViewModel);

        this.onTokensChangedListener = this::onTokensChangedListener;
        this.onNameChangedListener = this::onNameChangedListener;

        createView();
        bindViewModel();
    }

    private void onNameChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        snapToPointLater();
    }

    @Override
    public PlaceViewModel getViewModel() {
        return (PlaceViewModel) viewModel;
    }

    private void onTokensChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        snapToPointLater();
    }

    private void createView() {
        circle = new Circle();
        circle.setRadius(20);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        circle.setSmooth(true);
        circle.setStrokeType(StrokeType.OUTSIDE);

        tokensCountText = new Text("3");
        tokensCountText.setBoundsType(TextBoundsType.VISUAL);
        tokensCountText.setSmooth(true);
        tokensCountText.textProperty().addListener((observable, oldValue, newValue) -> {
            tokensCountText.setVisible(!newValue.equals("0"));
        });

        circleStack = new StackPane();
        circleStack.getChildren().add(circle);
        circleStack.getChildren().add(tokensCountText);
        circleStack.setMaxSize(0, 0);

        nameLabel = new Label("name");
//        name.setText("name name name");
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setMinWidth(Region.USE_PREF_SIZE);
//        name.setMaxWidth(Double.MAX_VALUE);

        container = new VBox();
        container.getChildren().add(circleStack);
        container.getChildren().add(nameLabel);
        container.setMaxHeight(0);
        container.setMaxWidth(0);
        container.setAlignment(Pos.CENTER);
        container.setFillWidth(false);

        if (false) {
            circleStack.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
            nameLabel.setBackground(new Background(new BackgroundFill(Color.AQUA, null, null)));
            container.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        }
    }

    @Override
    protected void bindViewModel() {
        super.bindViewModel();

        nameLabel.textProperty().bind(getViewModel().nameProperty());
        getViewModel().nameProperty().addListener(this.onNameChangedListener);

        tokensCountText.textProperty().bind(getViewModel().numberOfTokensProperty());
        getViewModel().numberOfTokensProperty().addListener(this.onTokensChangedListener);

        container.translateXProperty().bind(getViewModel().positionXProperty());
        container.translateYProperty().bind(getViewModel().positionYProperty());
    }

    @Override
    public void unbindViewModel() {
        nameLabel.textProperty().unbind();
        getViewModel().nameProperty().removeListener(this.onNameChangedListener);

        tokensCountText.textProperty().unbind();
        getViewModel().numberOfTokensProperty().removeListener(this.onTokensChangedListener);

        container.translateXProperty().unbind();
        container.translateYProperty().unbind();

        super.unbindViewModel();
    }

    @Override
    public Point2D getShapeCenter() {
        double x = circle.getLayoutX() + circleStack.getLayoutX() + container.getTranslateX();
        double y = circle.getLayoutY() + circleStack.getLayoutY() + container.getTranslateY();
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

    public VBox getRoot() {
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
        registerMouseHandlers(tokensCountText);
    }

    @Override
    public void removedFromParent() {
        unregisterMouseHandlers(circle);
        unregisterMouseHandlers(tokensCountText);
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
