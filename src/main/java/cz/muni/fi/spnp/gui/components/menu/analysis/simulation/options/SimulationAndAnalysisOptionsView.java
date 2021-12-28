package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.DoubleTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.IntegerTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MyDoubleStringConverter;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.IntegerStringConverter;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View with the simulation and analysis options.
 */
public class SimulationAndAnalysisOptionsView {

    private final SimulationOptionsViewModel simulationOptionsViewModel;
    private final AnalysisOptionsViewModel analysisOptionsViewModel;

    private GridPane gridPaneSimulation;
    private IntegerTextField textField_IOP_SIM_RUNS;
    private ChoiceBox<ConstantValue> choiceBox_IOP_SIM_RUNMETHOD;
    private IntegerTextField textField_IOP_SIM_SEED;
    private ChoiceBox<ConstantValue> choiceBox_IOP_SIM_CUMULATIVE;
    private ChoiceBox<ConstantValue> choiceBox_IOP_SIM_STD_REPORT;
    private IntegerTextField textField_IOP_SPLIT_LEVEL_DOWN;
    private ChoiceBox<ConstantValue> choiceBox_IOP_SPLIT_PRESIM;
    private IntegerTextField textField_IOP_SPLIT_NUMBER;
    private ChoiceBox<ConstantValue> choiceBox_IOP_SPLIT_RESTART_FINISH;
    private IntegerTextField textField_IOP_SPLIT_PRESIM_RUNS;
    private DoubleTextField textField_FOP_SIM_LENGTH;
    private DoubleTextField textField_FOP_SIM_CONFIDENCE;
    private DoubleTextField textField_FOP_SIM_ERROR;

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

        textField_IOP_SIM_RUNS = new IntegerTextField();
        addRow(gridPaneSimulation, new Label("IOP_SIM_RUNS"), textField_IOP_SIM_RUNS.getTextField());
        choiceBox_IOP_SIM_RUNMETHOD = new ChoiceBox<>(FXCollections.observableArrayList(
                VAL_REPL, VAL_BATCH, VAL_RESTART, VAL_SPLIT, VAL_IS, VAL_THIN, VAL_ISTHIN, VAL_REG, VAL_ISREG));
        addRow(gridPaneSimulation, new Label("IOP_SIM_RUNMETHOD"), choiceBox_IOP_SIM_RUNMETHOD);
        textField_IOP_SIM_SEED = new IntegerTextField();
        addRow(gridPaneSimulation, new Label("IOP_SIM_SEED"), textField_IOP_SIM_SEED.getTextField());
        choiceBox_IOP_SIM_CUMULATIVE = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneSimulation, new Label("IOP_SIM_CUMULATIVE"), choiceBox_IOP_SIM_CUMULATIVE);
        choiceBox_IOP_SIM_STD_REPORT = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneSimulation, new Label("IOP_SIM_STD_REPORT"), choiceBox_IOP_SIM_STD_REPORT);
        textField_IOP_SPLIT_LEVEL_DOWN = new IntegerTextField();
        addRow(gridPaneSimulation, new Label("IOP_SPLIT_LEVEL_DOWN"), textField_IOP_SPLIT_LEVEL_DOWN.getTextField());
        choiceBox_IOP_SPLIT_PRESIM = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneSimulation, new Label("IOP_SPLIT_PRESIM"), choiceBox_IOP_SPLIT_PRESIM);
        textField_IOP_SPLIT_NUMBER = new IntegerTextField();
        addRow(gridPaneSimulation, new Label("IOP_SPLIT_NUMBER"), textField_IOP_SPLIT_NUMBER.getTextField());
        choiceBox_IOP_SPLIT_RESTART_FINISH = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneSimulation, new Label("IOP_SPLIT_RESTART_FINISH"), choiceBox_IOP_SPLIT_RESTART_FINISH);
        textField_IOP_SPLIT_PRESIM_RUNS = new IntegerTextField();
        addRow(gridPaneSimulation, new Label("IOP_SPLIT_PRESIM_RUNS"), textField_IOP_SPLIT_PRESIM_RUNS.getTextField());
        textField_FOP_SIM_LENGTH = new DoubleTextField();
        addRow(gridPaneSimulation, new Label("FOP_SIM_LENGTH"), textField_FOP_SIM_LENGTH.getTextField());
        textField_FOP_SIM_CONFIDENCE = new DoubleTextField();
        addRow(gridPaneSimulation, new Label("FOP_SIM_CONFIDENCE"), textField_FOP_SIM_CONFIDENCE.getTextField());
        textField_FOP_SIM_ERROR = new DoubleTextField();
        addRow(gridPaneSimulation, new Label("FOP_SIM_ERROR"), textField_FOP_SIM_ERROR.getTextField());

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

        if (simulationOptionsViewModel.getIOP_SIMULATION() == VAL_YES) {
            radioButtonSimulation.setSelected(true);
        } else {
            radioButtonNumericAnalysis.setSelected(true);
        }
    }

    public Node getRoot() {
        return borderPane;
    }

    public void bindViewModels() {
        textField_IOP_SIM_RUNS.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.IOP_SIM_RUNSProperty().asObject(), new IntegerStringConverter());
        choiceBox_IOP_SIM_RUNMETHOD.valueProperty().bindBidirectional(simulationOptionsViewModel.IOP_SIM_RUNMETHODProperty());
        textField_IOP_SIM_SEED.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.IOP_SIM_SEEDProperty().asObject(), new IntegerStringConverter());
        choiceBox_IOP_SIM_CUMULATIVE.valueProperty().bindBidirectional(simulationOptionsViewModel.IOP_SIM_CUMULATIVEProperty());
        choiceBox_IOP_SIM_STD_REPORT.valueProperty().bindBidirectional(simulationOptionsViewModel.IOP_SIM_STD_REPORTProperty());
        textField_IOP_SPLIT_LEVEL_DOWN.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.IOP_SPLIT_LEVEL_DOWNProperty().asObject(), new IntegerStringConverter());
        choiceBox_IOP_SPLIT_PRESIM.valueProperty().bindBidirectional(simulationOptionsViewModel.IOP_SPLIT_PRESIMProperty());
        textField_IOP_SPLIT_NUMBER.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.IOP_SPLIT_NUMBERProperty().asObject(), new IntegerStringConverter());
        choiceBox_IOP_SPLIT_RESTART_FINISH.valueProperty().bindBidirectional(simulationOptionsViewModel.IOP_SPLIT_RESTART_FINISHProperty());
        textField_IOP_SPLIT_PRESIM_RUNS.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.IOP_SPLIT_PRESIM_RUNSProperty().asObject(), new IntegerStringConverter());
        textField_FOP_SIM_LENGTH.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.FOP_SIM_LENGTHProperty().asObject(), new MyDoubleStringConverter());
        textField_FOP_SIM_CONFIDENCE.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.FOP_SIM_CONFIDENCEProperty().asObject(), new MyDoubleStringConverter());
        textField_FOP_SIM_ERROR.getTextField().textProperty().bindBidirectional(simulationOptionsViewModel.FOP_SIM_ERRORProperty().asObject(), new MyDoubleStringConverter());

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
        textField_IOP_SIM_RUNS.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SIM_RUNSProperty().asObject());
        choiceBox_IOP_SIM_RUNMETHOD.valueProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SIM_RUNMETHODProperty());
        textField_IOP_SIM_SEED.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SIM_SEEDProperty().asObject());
        choiceBox_IOP_SIM_CUMULATIVE.valueProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SIM_CUMULATIVEProperty());
        choiceBox_IOP_SIM_STD_REPORT.valueProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SIM_STD_REPORTProperty());
        textField_IOP_SPLIT_LEVEL_DOWN.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SPLIT_LEVEL_DOWNProperty().asObject());
        choiceBox_IOP_SPLIT_PRESIM.valueProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SPLIT_PRESIMProperty());
        textField_IOP_SPLIT_NUMBER.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SPLIT_NUMBERProperty().asObject());
        choiceBox_IOP_SPLIT_RESTART_FINISH.valueProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SPLIT_RESTART_FINISHProperty());
        textField_IOP_SPLIT_PRESIM_RUNS.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.IOP_SPLIT_PRESIM_RUNSProperty().asObject());
        textField_FOP_SIM_LENGTH.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.FOP_SIM_LENGTHProperty().asObject());
        textField_FOP_SIM_CONFIDENCE.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.FOP_SIM_CONFIDENCEProperty().asObject());
        textField_FOP_SIM_ERROR.getTextField().textProperty().unbindBidirectional(simulationOptionsViewModel.FOP_SIM_ERRORProperty().asObject());

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
            simulationOptionsViewModel.IOP_SIMULATIONProperty().set(VAL_YES);
        }
    }

    private void onNumericAnalysisSelectedHandler(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        mainPane.getChildren().clear();

        if (newValue) {
            mainPane.getChildren().add(gridPaneNumericAnalysis);
            simulationOptionsViewModel.IOP_SIMULATIONProperty().set(VAL_NO);
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
