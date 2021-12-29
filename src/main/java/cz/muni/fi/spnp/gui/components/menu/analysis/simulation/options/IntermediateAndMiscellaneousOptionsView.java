package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View for the intermediate and miscellaneous options.
 */
public class IntermediateAndMiscellaneousOptionsView {

    private final IntermediateOptionsViewModel intermediateOptionsViewModel;
    private final MiscellaneousOptionsViewModel miscellaneousOptionsViewModel;

    private OptionsGridPane gridPaneIntermediate;
    private ChoiceOptionView IOP_PR_RSET;
    private ChoiceOptionView IOP_PR_RGRAPH;
    private ChoiceOptionView IOP_PR_MARK_ORDER;
    private ChoiceOptionView IOP_PR_MERG_MARK;
    private ChoiceOptionView IOP_PR_FULL_MARK;
    private ChoiceOptionView IOP_USENAME;
    private ChoiceOptionView IOP_PR_MC;
    private ChoiceOptionView IOP_PR_DERMC;
    private ChoiceOptionView IOP_PR_MC_ORDER;
    private ChoiceOptionView IOP_PR_PROB;
    private ChoiceOptionView IOP_PR_PROBDTMC;
    private ChoiceOptionView IOP_PR_DOT;

    private OptionsGridPane gridPaneMiscellaneous;
    private ChoiceOptionView IOP_ELIMINATION;
    private ChoiceOptionView IOP_OK_ABSMARK;
    private ChoiceOptionView IOP_OK_VANLOOP;
    private ChoiceOptionView IOP_OK_TRANS_M0;
    private ChoiceOptionView IOP_OK_VAN_M0;
    private DoubleOptionView FOP_ABS_RET_M0;
    private ChoiceOptionView IOP_DEBUG;
    private DoubleOptionView FOP_FLUID_EPSILON;
    private DoubleOptionView FOP_TIME_EPSILON;

    private VBox vBoxIntermediate;
    private VBox vBoxMiscellaneous;

    public IntermediateAndMiscellaneousOptionsView(DiagramViewModel diagramViewModel) {
        intermediateOptionsViewModel = diagramViewModel.getIntermediateOptions();
        miscellaneousOptionsViewModel = diagramViewModel.getMiscellaneousOptions();

        createView();
        bindViewModels();
    }

    private void createView() {

        gridPaneIntermediate = new OptionsGridPane();

        IOP_PR_RSET = new ChoiceOptionView("IOP_PR_RSET", FXCollections.observableArrayList(VAL_YES, VAL_NO, VAL_TAN));
        gridPaneIntermediate.addRow(IOP_PR_RSET);
        IOP_PR_RGRAPH = new ChoiceOptionView("IOP_PR_RGRAPH", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_RGRAPH);
        IOP_PR_MARK_ORDER = new ChoiceOptionView("IOP_PR_MARK_ORDER", FXCollections.observableArrayList(VAL_CANONIC, VAL_LEXICAL, VAL_MATRIX));
        gridPaneIntermediate.addRow(IOP_PR_MARK_ORDER);
        IOP_PR_MERG_MARK = new ChoiceOptionView("IOP_PR_MERG_MARK", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_MERG_MARK);
        IOP_PR_FULL_MARK = new ChoiceOptionView("IOP_PR_FULL_MARK", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_FULL_MARK);
        IOP_USENAME = new ChoiceOptionView("IOP_USENAME", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_USENAME);
        IOP_PR_MC = new ChoiceOptionView("IOP_PR_MC", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_MC);
        IOP_PR_DERMC = new ChoiceOptionView("IOP_PR_DERMC", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_DERMC);
        IOP_PR_MC_ORDER = new ChoiceOptionView("IOP_PR_MC_ORDER", FXCollections.observableArrayList(VAL_FROMTO, VAL_TOFROM));
        gridPaneIntermediate.addRow(IOP_PR_MC_ORDER);
        IOP_PR_PROB = new ChoiceOptionView("IOP_PR_PROB", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_PROB);
        IOP_PR_PROBDTMC = new ChoiceOptionView("IOP_PR_PROBDTMC", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_PROBDTMC);
        IOP_PR_DOT = new ChoiceOptionView("IOP_PR_DOT", createBooleanConstants());
        gridPaneIntermediate.addRow(IOP_PR_DOT);


        gridPaneMiscellaneous = new OptionsGridPane();

        IOP_ELIMINATION = new ChoiceOptionView("IOP_ELIMINATION", FXCollections.observableArrayList(VAL_REDONTHEFLY, VAL_REDAFTERRG, VAL_REDNEVER));
        gridPaneMiscellaneous.addRow(IOP_ELIMINATION);
        IOP_OK_ABSMARK = new ChoiceOptionView("IOP_OK_ABSMARK", createBooleanConstants());
        gridPaneMiscellaneous.addRow(IOP_OK_ABSMARK);
        IOP_OK_VANLOOP = new ChoiceOptionView("IOP_OK_VANLOOP", createBooleanConstants());
        gridPaneMiscellaneous.addRow(IOP_OK_VANLOOP);
        IOP_OK_TRANS_M0 = new ChoiceOptionView("IOP_OK_TRANS_M0", createBooleanConstants());
        gridPaneMiscellaneous.addRow(IOP_OK_TRANS_M0);
        IOP_OK_VAN_M0 = new ChoiceOptionView("IOP_OK_VAN_M0", createBooleanConstants());
        gridPaneMiscellaneous.addRow(IOP_OK_VAN_M0);
        FOP_ABS_RET_M0 = new DoubleOptionView("FOP_ABS_RET_M0");
        gridPaneMiscellaneous.addRow(FOP_ABS_RET_M0);
        IOP_DEBUG = new ChoiceOptionView("IOP_DEBUG", createBooleanConstants());
        gridPaneMiscellaneous.addRow(IOP_DEBUG);
        FOP_FLUID_EPSILON = new DoubleOptionView("FOP_FLUID_EPSILON");
        gridPaneMiscellaneous.addRow(FOP_FLUID_EPSILON);
        FOP_TIME_EPSILON = new DoubleOptionView("FOP_TIME_EPSILON");
        gridPaneMiscellaneous.addRow(FOP_TIME_EPSILON);

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
        IOP_PR_RSET.bind(intermediateOptionsViewModel.getIOP_PR_RSET());
        IOP_PR_RGRAPH.bind(intermediateOptionsViewModel.getIOP_PR_RGRAPH());
        IOP_PR_MARK_ORDER.bind(intermediateOptionsViewModel.getIOP_PR_MARK_ORDER());
        IOP_PR_MERG_MARK.bind(intermediateOptionsViewModel.getIOP_PR_MERG_MARK());
        IOP_PR_FULL_MARK.bind(intermediateOptionsViewModel.getIOP_PR_FULL_MARK());
        IOP_USENAME.bind(intermediateOptionsViewModel.getIOP_USENAME());
        IOP_PR_MC.bind(intermediateOptionsViewModel.getIOP_PR_MC());
        IOP_PR_DERMC.bind(intermediateOptionsViewModel.getIOP_PR_DERMC());
        IOP_PR_MC_ORDER.bind(intermediateOptionsViewModel.getIOP_PR_MC_ORDER());
        IOP_PR_PROB.bind(intermediateOptionsViewModel.getIOP_PR_PROB());
        IOP_PR_PROBDTMC.bind(intermediateOptionsViewModel.getIOP_PR_PROBDTMC());
        IOP_PR_DOT.bind(intermediateOptionsViewModel.getIOP_PR_DOT());

        IOP_ELIMINATION.bind(miscellaneousOptionsViewModel.getIOP_ELIMINATION());
        IOP_OK_ABSMARK.bind(miscellaneousOptionsViewModel.getIOP_OK_ABSMARK());
        IOP_OK_VANLOOP.bind(miscellaneousOptionsViewModel.getIOP_OK_VANLOOP());
        IOP_OK_TRANS_M0.bind(miscellaneousOptionsViewModel.getIOP_OK_TRANS_M0());
        IOP_OK_VAN_M0.bind(miscellaneousOptionsViewModel.getIOP_OK_VAN_M0());
        FOP_ABS_RET_M0.bind(miscellaneousOptionsViewModel.getFOP_ABS_RET_M0());
        IOP_DEBUG.bind(miscellaneousOptionsViewModel.getIOP_DEBUG());
        FOP_FLUID_EPSILON.bind(miscellaneousOptionsViewModel.getFOP_FLUID_EPSILON());
        FOP_TIME_EPSILON.bind(miscellaneousOptionsViewModel.getFOP_TIME_EPSILON());
    }

    public void unbindViewModels() {
        IOP_PR_RSET.unbind();
        IOP_PR_RGRAPH.unbind();
        IOP_PR_MARK_ORDER.unbind();
        IOP_PR_MERG_MARK.unbind();
        IOP_PR_FULL_MARK.unbind();
        IOP_USENAME.unbind();
        IOP_PR_MC.unbind();
        IOP_PR_DERMC.unbind();
        IOP_PR_MC_ORDER.unbind();
        IOP_PR_PROB.unbind();
        IOP_PR_PROBDTMC.unbind();
        IOP_PR_DOT.unbind();

        IOP_ELIMINATION.unbind();
        IOP_OK_ABSMARK.unbind();
        IOP_OK_VANLOOP.unbind();
        IOP_OK_TRANS_M0.unbind();
        IOP_OK_VAN_M0.unbind();
        FOP_ABS_RET_M0.unbind();
        IOP_DEBUG.unbind();
        FOP_FLUID_EPSILON.unbind();
        FOP_TIME_EPSILON.unbind();
    }

    private ObservableList<ConstantValue> createBooleanConstants() {
        return FXCollections.observableArrayList(VAL_YES, VAL_NO);
    }

    private void addRow(GridPane gridPane, Node left, Node right) {
        gridPane.addRow(gridPane.getRowCount(), left, right);
    }

}
