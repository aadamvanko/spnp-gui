package cz.muni.fi.spnp.gui.graph;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    GraphView graphView = new GraphView();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        graphView = new GraphView();

        root.setCenter(graphView.getRoot());

        Scene scene = new Scene(root, 1024, 768);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();

        Layout layout = new RandomLayout(graphView);
        layout.execute();

    }

    private void addGraphComponents() {

        Model model = graphView.getModel();

        graphView.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.RECTANGLE);
        model.addCell("Cell C", CellType.RECTANGLE);
        model.addCell("Cell D", CellType.TRIANGLE);
        model.addCell("Cell E", CellType.TRIANGLE);
        model.addCell("Cell F", CellType.RECTANGLE);
        model.addCell("Cell G", CellType.RECTANGLE);

        model.addEdge("Cell A", "Cell B");
        model.addEdge("Cell A", "Cell C");
        model.addEdge("Cell B", "Cell C");
        model.addEdge("Cell C", "Cell D");
        model.addEdge("Cell B", "Cell E");
        model.addEdge("Cell D", "Cell F");
        model.addEdge("Cell D", "Cell G");

        graphView.endUpdate();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
