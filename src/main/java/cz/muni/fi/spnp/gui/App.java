package cz.muni.fi.spnp.gui;

import cz.muni.fi.spnp.gui.components.mainwindow.ConfirmationWindowExit;
import cz.muni.fi.spnp.gui.components.mainwindow.MainWindowView;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.fileoperations.ModelLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Application class.
 */
public class App extends Application {

    private final Model model;

    public App() {
        this.model = new Model();
    }

    @Override
    public void start(Stage stage) throws IOException {
        (new ModelLoader()).loadSPNPPaths(model);
        MainWindowView mainWindowView = new MainWindowView(model);

        var scene = new Scene(mainWindowView.getRoot(), 1200, 600);
        stage.setScene(scene);
        stage.setTitle("SPNP GUIv2");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            var confirmationWindowExit = new ConfirmationWindowExit();
            confirmationWindowExit.getStage().showAndWait();
            if (!confirmationWindowExit.shouldExit()) {
                event.consume();
            }
        });
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        model.stopRunningSimulationTask();
        model.cleanUp();
    }

    public static void main(String[] args) {
        launch();
    }

}