package cz.muni.fi.spnp.gui;

import cz.muni.fi.spnp.gui.components.mainwindow.MainWindowController;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    public ModelLoader modelLoader;

    @Override
    public void start(Stage stage) throws IOException {
        Model model = new Model();
        (new ModelLoader()).loadSPNPPaths(model);
        MainWindowController mainWindowController = new MainWindowController(model);

        scene = new Scene(mainWindowController.getRoot(), 1600, 800);
        stage.setScene(scene);
        stage.setTitle("SPNP Editor");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}