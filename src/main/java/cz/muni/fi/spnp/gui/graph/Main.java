package cz.muni.fi.spnp.gui.graph;

import cz.muni.fi.spnp.gui.graph.canvas.ZoomableScrollPane;
import cz.muni.fi.spnp.gui.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.graph.elements.arc.ArcController;
import cz.muni.fi.spnp.gui.graph.elements.arc.InhibitorArcController;
import cz.muni.fi.spnp.gui.graph.elements.arc.StandardArcController;
import cz.muni.fi.spnp.gui.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.graph.elements.transition.TimedTransitionController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

//        Parent root = FXMLLoader.load(getClass().getResource("gui/mainwindow/sample.fxml"));
        primaryStage.setTitle("Hello World");

//        Group pane = new Group();
        Pane pane = new Pane();
        System.out.println(pane.getBoundsInParent().getWidth() + " - " + pane.getBoundsInParent().getHeight());

//        Group wrapper = new Group(pane);
//        place1.requestFocus();
        PlaceController pc1 = new PlaceController(100, 100);
//        pane.getChildren().add(pc1.getRoot());

        PlaceController pc2 = new PlaceController(500, 100);
//        pane.getChildren().add(pc2.getRoot());

        PlaceController pc4 = new PlaceController(100, 200);

        TimedTransitionController ttc1 = new TimedTransitionController(300, 100);
//        pane.getChildren().add(tc1.getRoot());

        ArcController ac1 = new StandardArcController(pc1, ttc1);
//        pane.getChildren().add(ac1.getRoot());

        ArcController ac2 = new StandardArcController(ttc1, pc2);
//        pane.getChildren().add(ac2.getRoot());
//        ac2.enableHighlight();

        ImmediateTransitionController itc1 = new ImmediateTransitionController(300, 200);

        ArcController ac3 = new InhibitorArcController(pc4, itc1);

        PlaceController pc3 = new PlaceController(100, 500);
//        pane.getChildren().add(pc1.getRoot());

        GraphView graphView = new GraphView();
        pc1.addToParent(graphView);
        pc2.addToParent(graphView);
        ttc1.addToParent(graphView);
        ac1.addToParent(graphView);
        ac2.addToParent(graphView);
        itc1.addToParent(graphView);
        pc3.addToParent(graphView);
        pc4.addToParent(graphView);
        ac3.addToParent(graphView);

//        Pane wrapper = new Pane(graphView.getZoomableScrollPane());
        Pane wrapper = new Pane();
        Pane p = new Pane();
        Group g = new Group();
        p.getChildren().add(g);
        ScrollPane scrollPane = new ScrollPane(p);
//        scrollPane.setPannable(true);
        ZoomableScrollPane zoomableScrollPane = new ZoomableScrollPane(p);
//        Pane w = new StackPane(graphView.getZoomableScrollPane());
//        w.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.SALMON, CornerRadii.EMPTY, Insets.EMPTY)}));
//        Scene scene = new Scene(w, 600, 400, false, SceneAntialiasing.BALANCED);
        var buttonView = new Button("View");
        buttonView.setOnAction(actionEvent -> {
            graphView.setCursorMode(CursorMode.VIEW);
        });
//        var buttonCreate = new Button("Create");
//        buttonCreate.setOnAction(actionEvent -> { graphView.setCursorMode(CursorMode.CREATE); });
        var buttonPlace = new Button("New Place");
        buttonPlace.setOnAction(actionEvent -> graphView.setCreateElementType(GraphElementType.PLACE));
        var buttonTimedTransition = new Button("New Timed T");
        buttonTimedTransition.setOnAction(actionEvent -> {
            graphView.setCreateElementType(GraphElementType.TIMED_TRANSITION);
        });
        var buttonImmediateTransition = new Button("New Immediate T");
        buttonImmediateTransition.setOnAction(actionEvent -> {
            graphView.setCreateElementType(GraphElementType.IMMEDIATE_TRANSITION);
        });
        var buttonStandardArc = new Button("New Standard A");
        buttonStandardArc.setOnAction(actionEvent -> {
            graphView.setCreateElementType(GraphElementType.STANDARD_ARC);
        });
        var buttonInhibitorArc = new Button("New Inhibitor A");
        buttonInhibitorArc.setOnAction(actionEvent -> graphView.setCreateElementType(GraphElementType.INHIBITOR_ARC));
        var checkboxSnapping = new CheckBox("snapping");
        checkboxSnapping.setSelected(true);
        checkboxSnapping.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                graphView.setSnappingToGrid(t1);
            }
        });
        HBox hbox = new HBox(buttonView, buttonPlace, buttonTimedTransition, buttonImmediateTransition, buttonStandardArc, buttonInhibitorArc,
                checkboxSnapping);
        GraphView graphView2 = new GraphView();
        HBox graphViewsBox = new HBox(graphView.getZoomableScrollPane(), graphView2.getZoomableScrollPane());
        VBox vbox = new VBox(hbox, graphViewsBox);
        Scene scene = new Scene(vbox, 600, 400, false, SceneAntialiasing.BALANCED);
//        Scene scene = new Scene(graphView.getZoomableScrollPane(), 600, 400, false, SceneAntialiasing.BALANCED);
        primaryStage.setScene(scene);

//        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private Circle createCircle(String strokeColor, String fillColor, double x) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(200);
        circle.setRadius(50);
        circle.setStroke(Color.valueOf(strokeColor));
        circle.setStrokeWidth(5);
        circle.setFill(Color.valueOf(fillColor));
        return circle;
    }

    private Rectangle createRectangle(String strokeColor, String fillColor, int x, int y, int sx, int sy) {
        Rectangle rectangle = new Rectangle();
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        rectangle.setWidth(sx);
        rectangle.setHeight(sy);
        rectangle.setStroke(Color.valueOf(strokeColor));
        rectangle.setStrokeWidth(1);
        rectangle.setFill(Color.valueOf(fillColor));
        return rectangle;
    }


    public static void main(String[] args) {
        launch(args);
    }
}


/*
   var vl = arcView.interactLine;
        vl.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(event);
            var b = new BoundingBox(event.getSceneX(), event.getSceneY(), 1, 1);
            for (int i = 0; i < vl.getPoints().size() - 2; i += 2) {
                var startX = vl.getPoints().get(i);
                var startY = vl.getPoints().get(i + 1);
                var endX = vl.getPoints().get(i + 2);
                var endY = vl.getPoints().get(i + 3);
                Line line = new Line(startX, startY, endX, endY);
                line.setTranslateX(vl.getTranslateX());
                line.setTranslateY(vl.getTranslateY());
                line.setStrokeWidth(vl.getStrokeWidth());
                if (line.intersects(b)) {
                    System.out.println("collision detected with line " + line);
                }
            }
        });
*/