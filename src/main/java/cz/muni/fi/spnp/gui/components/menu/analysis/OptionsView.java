package cz.muni.fi.spnp.gui.components.menu.analysis;

import cz.muni.fi.spnp.gui.components.common.UIWindowComponent;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.IntermediateAndMiscellaneousOptionsView;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.SimulationAndAnalysisOptionsView;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Contains all options simulation, analysis, intermediate and miscellaneous.
 */
public class OptionsView extends UIWindowComponent {

    private final DiagramViewModel diagramViewModel;

    private SimulationAndAnalysisOptionsView simulationAndAnalysisOptionsView;
    private IntermediateAndMiscellaneousOptionsView intermediateAndMiscellaneousOptionsView;

    public OptionsView(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;

        createView();
    }

    private void createView() {
        simulationAndAnalysisOptionsView = new SimulationAndAnalysisOptionsView(diagramViewModel);
        intermediateAndMiscellaneousOptionsView = new IntermediateAndMiscellaneousOptionsView(diagramViewModel);

        var simulationAnalysisTab = new Tab("Simulation & Analysis", simulationAndAnalysisOptionsView.getRoot());
        var intermediateTab = new Tab("Intermediate", intermediateAndMiscellaneousOptionsView.getIntermediateView());
        var miscellaneousTab = new Tab("Miscellaneous", intermediateAndMiscellaneousOptionsView.getMiscellaneousView());

        var tabPane = new TabPane(simulationAnalysisTab, intermediateTab, miscellaneousTab);

        var paneSpacer = new Pane();
        HBox.setHgrow(paneSpacer, Priority.ALWAYS);
        var buttonClose = new Button("Close");
        buttonClose.setOnAction(this::onButtonCloseHandler);

        var buttonsPane = new HBox(paneSpacer, buttonClose);
        buttonsPane.setSpacing(5);

        var borderPane = new BorderPane();
        borderPane.setCenter(tabPane);
        borderPane.setBottom(buttonsPane);
        borderPane.setPadding(new Insets(5));

        var scene = new Scene(borderPane);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                onButtonCloseHandler(null);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Options");
        stage.setMinWidth(400);
        stage.setMinHeight(600);
    }

    private void onButtonCloseHandler(ActionEvent actionEvent) {
        unbindViewModels();
        stage.close();
    }

    private void unbindViewModels() {
        simulationAndAnalysisOptionsView.unbindViewModels();
        intermediateAndMiscellaneousOptionsView.unbindViewModels();
    }

}
