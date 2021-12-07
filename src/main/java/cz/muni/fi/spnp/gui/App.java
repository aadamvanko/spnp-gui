package cz.muni.fi.spnp.gui;

import cz.muni.fi.spnp.gui.components.mainwindow.ConfirmationWindowExit;
import cz.muni.fi.spnp.gui.components.mainwindow.MainWindowView;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.fileoperations.ModelLoader;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.ExpectedNumberOfTokensAllPlacesSteadyState;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output.ExpectedNumberOfTokensAllPlacesTime;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
//
//        var f = new OutputOptionsView(model, model.selectedDiagramProperty().get());
//        f.getStage().showAndWait();
//
//        var f = new OptionsView(model, model.selectedDiagramProperty().get());
//        f.getStage().showAndWait();
//
        var option1 = new ExpectedNumberOfTokensAllPlacesTime();
        option1.timeProperty().set("1,2,3");
        model.getOutputOptions().add(option1);
        model.getOutputOptions().add(new ExpectedNumberOfTokensAllPlacesSteadyState());
        model.getOutputOptions().add(new ExpectedNumberOfTokensAllPlacesSteadyState());
//        var f = new CodePreviewView(model, model.selectedDiagramProperty().get());
//        f.getStage().showAndWait();

//        var cw = new ConfirmationWindowExit();
//        cw.getStage().showAndWait();
//        System.out.println(cw.shouldExit());

//        var v = new OptionsView(model, model.selectedDiagramProperty().get());
//        v.getStage().show();

//        var simpleDoubleProperty = new MySimpleDoubleProperty(0.001);
//        simpleDoubleProperty.addListener((observable, oldValue, newValue) -> {
//            System.out.println("oldValue " + oldValue + " newValue " + newValue);
//        });
//        var doubleTextField = new DoubleTextField();
//        doubleTextField.getTextField().textProperty().bindBidirectional(simpleDoubleProperty.asObject(), new MyDoubleStringConverter());
//        var scene = new Scene(new VBox(doubleTextField.getTextField()), 500, 200);
//        stage.setScene(scene);
//        stage.setTitle("App");
//        stage.show();
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