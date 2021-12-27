package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.DoubleTextField;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MyDoubleStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View for the intermediate and miscellaneous options.
 */
public class IntermediateAndMiscellaneousOptionsView {

    private final IntermediateOptionsViewModel intermediateOptionsViewModel;
    private final MiscellaneousOptionsViewModel miscellaneousOptionsViewModel;

    private GridPane gridPaneIntermediate;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_RSET;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_RGRAPH;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_MARK_ORDER;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_MERG_MARK;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_FULL_MARK;
    private ChoiceBox<ConstantValue> choiceBox_IOP_USENAME;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_MC;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_DERMC;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_MC_ORDER;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_PROB;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_PROBDTMC;
    private ChoiceBox<ConstantValue> choiceBox_IOP_PR_DOT;

    private GridPane gridPaneMiscellaneous;
    private ChoiceBox<ConstantValue> choiceBox_IOP_ELIMINATION;
    private ChoiceBox<ConstantValue> choiceBox_IOP_OK_ABSMARK;
    private ChoiceBox<ConstantValue> choiceBox_IOP_OK_VANLOOP;
    private ChoiceBox<ConstantValue> choiceBox_IOP_OK_TRANS_M0;
    private ChoiceBox<ConstantValue> choiceBox_IOP_OK_VAN_M0;
    private DoubleTextField textField_FOP_ABS_RET_M0;
    private ChoiceBox<ConstantValue> choiceBox_IOP_DEBUG;
    private DoubleTextField textField_FOP_FLUID_EPSILON;
    private DoubleTextField textField_FOP_TIME_EPSILON;
    private VBox vBoxIntermediate;
    private VBox vBoxMiscellaneous;

    public IntermediateAndMiscellaneousOptionsView(DiagramViewModel diagramViewModel) {
        intermediateOptionsViewModel = diagramViewModel.getIntermediateOptions();
        miscellaneousOptionsViewModel = diagramViewModel.getMiscellaneousOptions();

        createView();
        bindViewModels();
    }

    private void createView() {

        gridPaneIntermediate = new GridPane();
        gridPaneIntermediate.setHgap(5);
        gridPaneIntermediate.setVgap(5);

        choiceBox_IOP_PR_RSET = new ChoiceBox<>(FXCollections.observableArrayList(VAL_YES, VAL_NO, VAL_TAN));
        addRow(gridPaneIntermediate, new Label("IOP_PR_RSET"), choiceBox_IOP_PR_RSET);
        choiceBox_IOP_PR_RGRAPH = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_RGRAPH"), choiceBox_IOP_PR_RGRAPH);
        choiceBox_IOP_PR_MARK_ORDER = new ChoiceBox<>(FXCollections.observableArrayList(VAL_CANONIC, VAL_LEXICAL, VAL_MATRIX));
        addRow(gridPaneIntermediate, new Label("IOP_PR_MARK_ORDER"), choiceBox_IOP_PR_MARK_ORDER);
        choiceBox_IOP_PR_MERG_MARK = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_MERG_MARK"), choiceBox_IOP_PR_MERG_MARK);
        choiceBox_IOP_PR_FULL_MARK = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_FULL_MARK"), choiceBox_IOP_PR_FULL_MARK);
        choiceBox_IOP_USENAME = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_USENAME"), choiceBox_IOP_USENAME);
        choiceBox_IOP_PR_MC = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_MC"), choiceBox_IOP_PR_MC);
        choiceBox_IOP_PR_DERMC = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_DERMC"), choiceBox_IOP_PR_DERMC);
        choiceBox_IOP_PR_MC_ORDER = new ChoiceBox<>(FXCollections.observableArrayList(VAL_FROMTO, VAL_TOFROM));
        addRow(gridPaneIntermediate, new Label("IOP_PR_MC_ORDER"), choiceBox_IOP_PR_MC_ORDER);
        choiceBox_IOP_PR_PROB = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_PROB"), choiceBox_IOP_PR_PROB);
        choiceBox_IOP_PR_PROBDTMC = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_PROBDTMC"), choiceBox_IOP_PR_PROBDTMC);
        choiceBox_IOP_PR_DOT = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneIntermediate, new Label("IOP_PR_DOT"), choiceBox_IOP_PR_DOT);


        gridPaneMiscellaneous = new GridPane();
        gridPaneMiscellaneous.setHgap(5);
        gridPaneMiscellaneous.setVgap(5);

        choiceBox_IOP_ELIMINATION = new ChoiceBox<>(FXCollections.observableArrayList(VAL_REDONTHEFLY, VAL_REDAFTERRG, VAL_REDNEVER));
        addRow(gridPaneMiscellaneous, new Label("IOP_ELIMINATION"), choiceBox_IOP_ELIMINATION);
        choiceBox_IOP_OK_ABSMARK = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneMiscellaneous, new Label("IOP_OK_ABSMARK"), choiceBox_IOP_OK_ABSMARK);
        choiceBox_IOP_OK_VANLOOP = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneMiscellaneous, new Label("IOP_OK_VANLOOP"), choiceBox_IOP_OK_VANLOOP);
        choiceBox_IOP_OK_TRANS_M0 = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneMiscellaneous, new Label("IOP_OK_TRANS_M0"), choiceBox_IOP_OK_TRANS_M0);
        choiceBox_IOP_OK_VAN_M0 = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneMiscellaneous, new Label("IOP_OK_VAN_M0"), choiceBox_IOP_OK_VAN_M0);
        textField_FOP_ABS_RET_M0 = new DoubleTextField();
        addRow(gridPaneMiscellaneous, new Label("FOP_ABS_RET_M0"), textField_FOP_ABS_RET_M0.getTextField());
        choiceBox_IOP_DEBUG = new ChoiceBox<>(createBooleanConstants());
        addRow(gridPaneMiscellaneous, new Label("IOP_DEBUG"), choiceBox_IOP_DEBUG);
        textField_FOP_FLUID_EPSILON = new DoubleTextField();
        addRow(gridPaneMiscellaneous, new Label("FOP_FLUID_EPSILON"), textField_FOP_FLUID_EPSILON.getTextField());
        textField_FOP_TIME_EPSILON = new DoubleTextField();
        addRow(gridPaneMiscellaneous, new Label("FOP_TIME_EPSILON"), textField_FOP_TIME_EPSILON.getTextField());

        vBoxIntermediate = new VBox(gridPaneIntermediate);
        vBoxIntermediate.setPadding(new Insets(5));

        vBoxMiscellaneous = new VBox(gridPaneMiscellaneous);
        vBoxMiscellaneous.setPadding(new Insets(5));
    }

    public Node getIntermediateView() {
        return vBoxIntermediate;
    }

    public Node getMiscellaneousView() {
        return vBoxMiscellaneous;
    }

    public void bindViewModels() {
        choiceBox_IOP_PR_RSET.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_RSETProperty());
        choiceBox_IOP_PR_RGRAPH.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_RGRAPHProperty());
        choiceBox_IOP_PR_MARK_ORDER.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_MARK_ORDERProperty());
        choiceBox_IOP_PR_MERG_MARK.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_MERG_MARKProperty());
        choiceBox_IOP_PR_FULL_MARK.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_FULL_MARKProperty());
        choiceBox_IOP_USENAME.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_USENAMEProperty());
        choiceBox_IOP_PR_MC.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_MCProperty());
        choiceBox_IOP_PR_DERMC.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_DERMCProperty());
        choiceBox_IOP_PR_MC_ORDER.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_MC_ORDERProperty());
        choiceBox_IOP_PR_PROB.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_PROBProperty());
        choiceBox_IOP_PR_PROBDTMC.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_PROBDTMCProperty());
        choiceBox_IOP_PR_DOT.valueProperty().bindBidirectional(intermediateOptionsViewModel.IOP_PR_DOTProperty());

        choiceBox_IOP_ELIMINATION.valueProperty().bindBidirectional(miscellaneousOptionsViewModel.IOP_ELIMINATIONProperty());
        choiceBox_IOP_OK_ABSMARK.valueProperty().bindBidirectional(miscellaneousOptionsViewModel.IOP_OK_ABSMARKProperty());
        choiceBox_IOP_OK_VANLOOP.valueProperty().bindBidirectional(miscellaneousOptionsViewModel.IOP_OK_VANLOOPProperty());
        choiceBox_IOP_OK_TRANS_M0.valueProperty().bindBidirectional(miscellaneousOptionsViewModel.IOP_OK_TRANS_M0Property());
        choiceBox_IOP_OK_VAN_M0.valueProperty().bindBidirectional(miscellaneousOptionsViewModel.IOP_OK_VAN_M0Property());
        textField_FOP_ABS_RET_M0.getTextField().textProperty().bindBidirectional(miscellaneousOptionsViewModel.FOP_ABS_RET_M0Property().asObject(), new MyDoubleStringConverter());
        choiceBox_IOP_DEBUG.valueProperty().bindBidirectional(miscellaneousOptionsViewModel.IOP_DEBUGProperty());
        textField_FOP_FLUID_EPSILON.getTextField().textProperty().bindBidirectional(miscellaneousOptionsViewModel.FOP_FLUID_EPSILONProperty().asObject(), new MyDoubleStringConverter());
        textField_FOP_TIME_EPSILON.getTextField().textProperty().bindBidirectional(miscellaneousOptionsViewModel.FOP_TIME_EPSILONProperty().asObject(), new MyDoubleStringConverter());
    }

    public void unbindViewModels() {
        choiceBox_IOP_PR_RSET.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_RSETProperty());
        choiceBox_IOP_PR_RGRAPH.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_RGRAPHProperty());
        choiceBox_IOP_PR_MARK_ORDER.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_MARK_ORDERProperty());
        choiceBox_IOP_PR_MERG_MARK.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_MERG_MARKProperty());
        choiceBox_IOP_PR_FULL_MARK.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_FULL_MARKProperty());
        choiceBox_IOP_USENAME.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_USENAMEProperty());
        choiceBox_IOP_PR_MC.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_MCProperty());
        choiceBox_IOP_PR_DERMC.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_DERMCProperty());
        choiceBox_IOP_PR_MC_ORDER.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_MC_ORDERProperty());
        choiceBox_IOP_PR_PROB.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_PROBProperty());
        choiceBox_IOP_PR_PROBDTMC.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_PROBDTMCProperty());
        choiceBox_IOP_PR_DOT.valueProperty().unbindBidirectional(intermediateOptionsViewModel.IOP_PR_DOTProperty());

        choiceBox_IOP_ELIMINATION.valueProperty().unbindBidirectional(miscellaneousOptionsViewModel.IOP_ELIMINATIONProperty());
        choiceBox_IOP_OK_ABSMARK.valueProperty().unbindBidirectional(miscellaneousOptionsViewModel.IOP_OK_ABSMARKProperty());
        choiceBox_IOP_OK_VANLOOP.valueProperty().unbindBidirectional(miscellaneousOptionsViewModel.IOP_OK_VANLOOPProperty());
        choiceBox_IOP_OK_TRANS_M0.valueProperty().unbindBidirectional(miscellaneousOptionsViewModel.IOP_OK_TRANS_M0Property());
        choiceBox_IOP_OK_VAN_M0.valueProperty().unbindBidirectional(miscellaneousOptionsViewModel.IOP_OK_VAN_M0Property());
        textField_FOP_ABS_RET_M0.getTextField().textProperty().unbindBidirectional(miscellaneousOptionsViewModel.FOP_ABS_RET_M0Property().asObject());
        choiceBox_IOP_DEBUG.valueProperty().unbindBidirectional(miscellaneousOptionsViewModel.IOP_DEBUGProperty());
        textField_FOP_FLUID_EPSILON.getTextField().textProperty().unbindBidirectional(miscellaneousOptionsViewModel.FOP_FLUID_EPSILONProperty().asObject());
        textField_FOP_TIME_EPSILON.getTextField().textProperty().unbindBidirectional(miscellaneousOptionsViewModel.FOP_TIME_EPSILONProperty().asObject());
    }

    private ObservableList<ConstantValue> createBooleanConstants() {
        return FXCollections.observableArrayList(VAL_YES, VAL_NO);
    }

    private void addRow(GridPane gridPane, Node left, Node right) {
        gridPane.addRow(gridPane.getRowCount(), left, right);
    }

}
