package cz.muni.fi.spnp.gui;

import cz.muni.fi.spnp.gui.components.mainwindow.MainWindowView;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.CodePreviewView;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.ExpectedNumberOfTokensAllPlacesSteadyState;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.ExpectedNumberOfTokensAllPlacesTime;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private final Model model;

    public App() {
        this.model = new Model();
    }

    @Override
    public void start(Stage stage) throws IOException {
        (new ModelLoader()).loadSPNPPaths(model);
        MainWindowView mainWindowView = new MainWindowView(model);

        var scene = new Scene(mainWindowView.getRoot(), 1600, 800);
        stage.setScene(scene);
        stage.setTitle("SPNP Editor");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
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