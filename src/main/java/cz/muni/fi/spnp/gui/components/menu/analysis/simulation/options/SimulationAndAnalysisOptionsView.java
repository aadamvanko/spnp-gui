package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View with the simulation and analysis options.
 */
public class SimulationAndAnalysisOptionsView {

    private final SimulationOptionsViewModel simulationOptionsViewModel;
    private final AnalysisOptionsViewModel analysisOptionsViewModel;

    private GridPane gridPaneSimulation;
    private IntegerOptionView IOP_SIM_RUNS;
    private ChoiceOptionView IOP_SIM_RUNMETHOD;
    private IntegerOptionView IOP_SIM_SEED;
    private ChoiceOptionView IOP_SIM_CUMULATIVE;
    private ChoiceOptionView IOP_SIM_STD_REPORT;
    private IntegerOptionView IOP_SPLIT_LEVEL_DOWN;
    private ChoiceOptionView IOP_SPLIT_PRESIM;
    private IntegerOptionView IOP_SPLIT_NUMBER;
    private ChoiceOptionView IOP_SPLIT_RESTART_FINISH;
    private IntegerOptionView IOP_SPLIT_PRESIM_RUNS;
    private DoubleOptionView FOP_SIM_LENGTH;
    private DoubleOptionView FOP_SIM_CONFIDENCE;
    private DoubleOptionView FOP_SIM_ERROR;

    private GridPane gridPaneNumericAnalysis;
    private ChoiceOptionView IOP_MC;
    private ChoiceOptionView IOP_SSMETHOD;
    private ChoiceOptionView IOP_SSDETECT;
    private DoubleOptionView FOP_SSPRES;
    private ChoiceOptionView IOP_TSMETHOD;
    private ChoiceOptionView IOP_CUMULATIVE;
    private ChoiceOptionView IOP_SENSITIVTY;
    private IntegerOptionView IOP_ITERATIONS;
    private DoubleOptionView FOP_PRECISION;

    private Pane mainPane;
    private BorderPane borderPane;

    public SimulationAndAnalysisOptionsView(DiagramViewModel diagramViewModel) {
        simulationOptionsViewModel = diagramViewModel.getSimulationOptions();
        analysisOptionsViewModel = diagramViewModel.getAnalysisOptions();

        createView();
        bindViewModels();
    }

    private void createView() {

        var labelTitle = new Label("Solution method:");

        var toggleGroup = new ToggleGroup();
        var radioButtonSimulation = new RadioButton("Simulation");
        radioButtonSimulation.setToggleGroup(toggleGroup);
        radioButtonSimulation.selectedProperty().addListener(this::onSimulationSelectedHandler);

        var spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        var radioButtonNumericAnalysis = new RadioButton("Numeric analysis");
        radioButtonNumericAnalysis.selectedProperty().addListener(this::onNumericAnalysisSelectedHandler);
        radioButtonNumericAnalysis.setToggleGroup(toggleGroup);

        var hboxSimulationMethods = new HBox(radioButtonSimulation, spacer, radioButtonNumericAnalysis);

        mainPane = new Pane();
        mainPane.setPadding(new Insets(5));

        gridPaneSimulation = new GridPane();
        gridPaneSimulation.setHgap(5);
        gridPaneSimulation.setVgap(5);
        gridPaneSimulation.addRow(0, new Label("Option"), new Label("Value"), new Label("Use"));

        IOP_SIM_RUNS = new IntegerOptionView("IOP_SIM_RUNS");
        addRow(gridPaneSimulation, IOP_SIM_RUNS);
        IOP_SIM_RUNMETHOD = new ChoiceOptionView("IOP_SIM_RUNMETHOD",
                FXCollections.observableArrayList(VAL_REPL, VAL_BATCH, VAL_RESTART, VAL_SPLIT, VAL_IS, VAL_THIN, VAL_ISTHIN, VAL_REG, VAL_ISREG));
        addRow(gridPaneSimulation, IOP_SIM_RUNMETHOD);
        IOP_SIM_SEED = new IntegerOptionView("IOP_SIM_SEED");
        addRow(gridPaneSimulation, IOP_SIM_SEED);
        IOP_SIM_CUMULATIVE = new ChoiceOptionView("IOP_SIM_CUMULATIVE", createBooleanConstants());
        addRow(gridPaneSimulation, IOP_SIM_CUMULATIVE);
        IOP_SIM_STD_REPORT = new ChoiceOptionView("IOP_SIM_STD_REPORT", createBooleanConstants());
        addRow(gridPaneSimulation, IOP_SIM_STD_REPORT);
        IOP_SPLIT_LEVEL_DOWN = new IntegerOptionView("IOP_SPLIT_LEVEL_DOWN");
        addRow(gridPaneSimulation, IOP_SPLIT_LEVEL_DOWN);
        IOP_SPLIT_PRESIM = new ChoiceOptionView("IOP_SPLIT_PRESIM", createBooleanConstants());
        addRow(gridPaneSimulation, IOP_SPLIT_PRESIM);
        IOP_SPLIT_NUMBER = new IntegerOptionView("IOP_SPLIT_NUMBER");
        addRow(gridPaneSimulation, IOP_SPLIT_NUMBER);
        IOP_SPLIT_RESTART_FINISH = new ChoiceOptionView("IOP_SPLIT_RESTART_FINISH", createBooleanConstants());
        addRow(gridPaneSimulation, IOP_SPLIT_RESTART_FINISH);
        IOP_SPLIT_PRESIM_RUNS = new IntegerOptionView("IOP_SPLIT_PRESIM_RUNS");
        addRow(gridPaneSimulation, IOP_SPLIT_PRESIM_RUNS);
        FOP_SIM_LENGTH = new DoubleOptionView("FOP_SIM_LENGTH");
        addRow(gridPaneSimulation, FOP_SIM_LENGTH);
        FOP_SIM_CONFIDENCE = new DoubleOptionView("FOP_SIM_CONFIDENCE");
        addRow(gridPaneSimulation, FOP_SIM_CONFIDENCE);
        FOP_SIM_ERROR = new DoubleOptionView("FOP_SIM_ERROR");
        addRow(gridPaneSimulation, FOP_SIM_ERROR);

        gridPaneNumericAnalysis = new GridPane();
        gridPaneNumericAnalysis.setHgap(5);
        gridPaneNumericAnalysis.setVgap(5);
        gridPaneNumericAnalysis.addRow(0, new Label("Option"), new Label("Value"), new Label("Use"));

        IOP_MC = new ChoiceOptionView("IOP_MC", FXCollections.observableArrayList(VAL_CTMC, VAL_DTMC));
        addRow(gridPaneNumericAnalysis, IOP_MC);
        IOP_SSMETHOD = new ChoiceOptionView("IOP_SSMETHOD", FXCollections.observableArrayList(VAL_SSSOR, VAL_GASEI, VAL_POWER));
        addRow(gridPaneNumericAnalysis, IOP_SSMETHOD);
        IOP_SSDETECT = new ChoiceOptionView("IOP_SSDETECT", createBooleanConstants());
        addRow(gridPaneNumericAnalysis, IOP_SSDETECT);
        FOP_SSPRES = new DoubleOptionView("FOP_SSPRES");
        addRow(gridPaneNumericAnalysis, FOP_SSPRES);
        IOP_TSMETHOD = new ChoiceOptionView("IOP_TSMETHOD", FXCollections.observableArrayList(VAL_TSUNIF, VAL_FOXUNIF));
        addRow(gridPaneNumericAnalysis, IOP_TSMETHOD);
        IOP_CUMULATIVE = new ChoiceOptionView("IOP_CUMULATIVE", createBooleanConstants());
        addRow(gridPaneNumericAnalysis, IOP_CUMULATIVE);
        IOP_SENSITIVTY = new ChoiceOptionView("IOP_SENSITIVITY", createBooleanConstants());
        addRow(gridPaneNumericAnalysis, IOP_SENSITIVTY);
        IOP_ITERATIONS = new IntegerOptionView("IOP_ITERATIONS");
        addRow(gridPaneNumericAnalysis, IOP_ITERATIONS);
        FOP_PRECISION = new DoubleOptionView("FOP_PRECISION");
        addRow(gridPaneNumericAnalysis, FOP_PRECISION);

        var topPane = new VBox(labelTitle, hboxSimulationMethods, new Separator(Orientation.HORIZONTAL));
        topPane.setSpacing(5);

        borderPane = new BorderPane();
        borderPane.setTop(topPane);
        borderPane.setCenter(mainPane);
        borderPane.setPadding(new Insets(5));

        if (simulationOptionsViewModel.getIOP_SIMULATION().getValue() == VAL_YES) {
            radioButtonSimulation.setSelected(true);
        } else {
            radioButtonNumericAnalysis.setSelected(true);
        }
    }

    public Node getRoot() {
        return borderPane;
    }

    public void bindViewModels() {
        IOP_SIM_RUNS.bind(simulationOptionsViewModel.getIOP_SIM_RUNS());
        IOP_SIM_RUNMETHOD.bind(simulationOptionsViewModel.getIOP_SIM_RUNMETHOD());
        IOP_SIM_SEED.bind(simulationOptionsViewModel.getIOP_SIM_SEED());
        IOP_SIM_CUMULATIVE.bind(simulationOptionsViewModel.getIOP_SIM_CUMULATIVE());
        IOP_SIM_STD_REPORT.bind(simulationOptionsViewModel.getIOP_SIM_STD_REPORT());
        IOP_SPLIT_LEVEL_DOWN.bind(simulationOptionsViewModel.getIOP_SPLIT_LEVEL_DOWN());
        IOP_SPLIT_PRESIM.bind(simulationOptionsViewModel.getIOP_SPLIT_PRESIM());
        IOP_SPLIT_NUMBER.bind(simulationOptionsViewModel.getIOP_SPLIT_NUMBER());
        IOP_SPLIT_RESTART_FINISH.bind(simulationOptionsViewModel.getIOP_SPLIT_RESTART_FINISH());
        IOP_SPLIT_PRESIM_RUNS.bind(simulationOptionsViewModel.getIOP_SPLIT_PRESIM_RUNS());
        FOP_SIM_LENGTH.bind(simulationOptionsViewModel.getFOP_SIM_LENGTH());
        FOP_SIM_CONFIDENCE.bind(simulationOptionsViewModel.getFOP_SIM_CONFIDENCE());
        FOP_SIM_ERROR.bind(simulationOptionsViewModel.getFOP_SIM_ERROR());

        IOP_MC.bind(analysisOptionsViewModel.getIOP_MC());
        IOP_SSMETHOD.bind(analysisOptionsViewModel.getIOP_SSMETHOD());
        IOP_SSDETECT.bind(analysisOptionsViewModel.getIOP_SSDETECT());
        FOP_SSPRES.bind(analysisOptionsViewModel.getFOP_SSPRES());
        IOP_TSMETHOD.bind(analysisOptionsViewModel.getIOP_TSMETHOD());
        IOP_CUMULATIVE.bind(analysisOptionsViewModel.getIOP_CUMULATIVE());
        IOP_SENSITIVTY.bind(analysisOptionsViewModel.getIOP_SENSITIVITY());
        IOP_ITERATIONS.bind(analysisOptionsViewModel.getIOP_ITERATIONS());
        FOP_PRECISION.bind(analysisOptionsViewModel.getFOP_PRECISION());
    }

    public void unbindViewModels() {
        IOP_SIM_RUNS.unbind();
        IOP_SIM_RUNMETHOD.unbind();
        IOP_SIM_SEED.unbind();
        IOP_SIM_CUMULATIVE.unbind();
        IOP_SIM_STD_REPORT.unbind();
        IOP_SPLIT_LEVEL_DOWN.unbind();
        IOP_SPLIT_PRESIM.unbind();
        IOP_SPLIT_NUMBER.unbind();
        IOP_SPLIT_RESTART_FINISH.unbind();
        IOP_SPLIT_PRESIM_RUNS.unbind();
        FOP_SIM_LENGTH.unbind();
        FOP_SIM_CONFIDENCE.unbind();
        FOP_SIM_ERROR.unbind();

        IOP_MC.unbind();
        IOP_SSMETHOD.unbind();
        IOP_SSDETECT.unbind();
        FOP_SSPRES.unbind();
        IOP_TSMETHOD.unbind();
        IOP_CUMULATIVE.unbind();
        IOP_SENSITIVTY.unbind();
        IOP_ITERATIONS.unbind();
        FOP_PRECISION.unbind();
    }

    private void onSimulationSelectedHandler(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        mainPane.getChildren().clear();

        if (newValue) {
            mainPane.getChildren().add(gridPaneSimulation);
            simulationOptionsViewModel.getIOP_SIMULATION().valueProperty().set(VAL_YES);
        }
    }

    private void onNumericAnalysisSelectedHandler(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        mainPane.getChildren().clear();

        if (newValue) {
            mainPane.getChildren().add(gridPaneNumericAnalysis);
            simulationOptionsViewModel.getIOP_SIMULATION().valueProperty().set(VAL_NO);
        }
    }

    private ObservableList<ConstantValue> createBooleanConstants() {
        return FXCollections.observableArrayList(VAL_YES, VAL_NO);
    }

    private void addRow(GridPane gridPane, Node left, Node right) {
        gridPane.addRow(gridPane.getRowCount(), left, right);
    }

    private void addRow(GridPane gridPane, ChoiceOptionView choiceOptionView) {
        gridPane.addRow(gridPane.getRowCount(), choiceOptionView.getLabelName(), choiceOptionView.getChoiceBoxValue(), choiceOptionView.getCheckBoxUse());
    }

    private void addRow(GridPane gridPane, IntegerOptionView integerOptionView) {
        gridPane.addRow(gridPane.getRowCount(), integerOptionView.getLabelName(),
                integerOptionView.getIntegerTextFieldValue().getTextField(), integerOptionView.getCheckBoxUse());
    }

    private void addRow(GridPane gridPane, DoubleOptionView doubleOptionView) {
        gridPane.addRow(gridPane.getRowCount(), doubleOptionView.getLabelName(),
                doubleOptionView.getDoubleTextFieldValue().getTextField(), doubleOptionView.getCheckBoxUse());
    }

}
