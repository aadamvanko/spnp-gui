package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleDoubleProperty;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View model of the simulation options.
 */
public class SimulationOptionsViewModel {

    private final ObjectProperty<ConstantValue> IOP_SIMULATION;
    private final IntegerProperty IOP_SIM_RUNS;
    private final ObjectProperty<ConstantValue> IOP_SIM_RUNMETHOD;
    private final IntegerProperty IOP_SIM_SEED;
    private final ObjectProperty<ConstantValue> IOP_SIM_CUMULATIVE;
    private final ObjectProperty<ConstantValue> IOP_SIM_STD_REPORT;
    private final IntegerProperty IOP_SPLIT_LEVEL_DOWN;
    private final ObjectProperty<ConstantValue> IOP_SPLIT_PRESIM;
    private final IntegerProperty IOP_SPLIT_NUMBER;
    private final ObjectProperty<ConstantValue> IOP_SPLIT_RESTART_FINISH;
    private final IntegerProperty IOP_SPLIT_PRESIM_RUNS;
    private final DoubleProperty FOP_SIM_LENGTH;
    private final DoubleProperty FOP_SIM_CONFIDENCE;
    private final DoubleProperty FOP_SIM_ERROR;

    public SimulationOptionsViewModel() {
        this.IOP_SIMULATION = new SimpleObjectProperty<>(VAL_YES);
        this.IOP_SIM_RUNS = new MySimpleIntegerProperty(0);
        this.IOP_SIM_RUNMETHOD = new SimpleObjectProperty<>(VAL_REPL);
        this.IOP_SIM_SEED = new MySimpleIntegerProperty(52836);
        this.IOP_SIM_CUMULATIVE = new SimpleObjectProperty<>(VAL_YES);
        this.IOP_SIM_STD_REPORT = new SimpleObjectProperty<>(VAL_YES);
        this.IOP_SPLIT_LEVEL_DOWN = new MySimpleIntegerProperty(60);
        this.IOP_SPLIT_PRESIM = new SimpleObjectProperty<>(VAL_YES);
        this.IOP_SPLIT_NUMBER = new MySimpleIntegerProperty(6);
        this.IOP_SPLIT_RESTART_FINISH = new SimpleObjectProperty<>(VAL_NO);
        this.IOP_SPLIT_PRESIM_RUNS = new MySimpleIntegerProperty(10);
        this.FOP_SIM_LENGTH = new MySimpleDoubleProperty(0.0);
        this.FOP_SIM_CONFIDENCE = new MySimpleDoubleProperty(0.95);
        this.FOP_SIM_ERROR = new MySimpleDoubleProperty(0.1);
    }

    public ConstantValue getIOP_SIMULATION() {
        return IOP_SIMULATION.get();
    }

    public ObjectProperty<ConstantValue> IOP_SIMULATIONProperty() {
        return IOP_SIMULATION;
    }

    public int getIOP_SIM_RUNS() {
        return IOP_SIM_RUNS.get();
    }

    public IntegerProperty IOP_SIM_RUNSProperty() {
        return IOP_SIM_RUNS;
    }

    public ConstantValue getIOP_SIM_RUNMETHOD() {
        return IOP_SIM_RUNMETHOD.get();
    }

    public ObjectProperty<ConstantValue> IOP_SIM_RUNMETHODProperty() {
        return IOP_SIM_RUNMETHOD;
    }

    public int getIOP_SIM_SEED() {
        return IOP_SIM_SEED.get();
    }

    public IntegerProperty IOP_SIM_SEEDProperty() {
        return IOP_SIM_SEED;
    }

    public ConstantValue getIOP_SIM_CUMULATIVE() {
        return IOP_SIM_CUMULATIVE.get();
    }

    public ObjectProperty<ConstantValue> IOP_SIM_CUMULATIVEProperty() {
        return IOP_SIM_CUMULATIVE;
    }

    public ConstantValue getIOP_SIM_STD_REPORT() {
        return IOP_SIM_STD_REPORT.get();
    }

    public ObjectProperty<ConstantValue> IOP_SIM_STD_REPORTProperty() {
        return IOP_SIM_STD_REPORT;
    }

    public int getIOP_SPLIT_LEVEL_DOWN() {
        return IOP_SPLIT_LEVEL_DOWN.get();
    }

    public IntegerProperty IOP_SPLIT_LEVEL_DOWNProperty() {
        return IOP_SPLIT_LEVEL_DOWN;
    }

    public ConstantValue getIOP_SPLIT_PRESIM() {
        return IOP_SPLIT_PRESIM.get();
    }

    public ObjectProperty<ConstantValue> IOP_SPLIT_PRESIMProperty() {
        return IOP_SPLIT_PRESIM;
    }

    public int getIOP_SPLIT_NUMBER() {
        return IOP_SPLIT_NUMBER.get();
    }

    public IntegerProperty IOP_SPLIT_NUMBERProperty() {
        return IOP_SPLIT_NUMBER;
    }

    public ConstantValue getIOP_SPLIT_RESTART_FINISH() {
        return IOP_SPLIT_RESTART_FINISH.get();
    }

    public ObjectProperty<ConstantValue> IOP_SPLIT_RESTART_FINISHProperty() {
        return IOP_SPLIT_RESTART_FINISH;
    }

    public int getIOP_SPLIT_PRESIM_RUNS() {
        return IOP_SPLIT_PRESIM_RUNS.get();
    }

    public IntegerProperty IOP_SPLIT_PRESIM_RUNSProperty() {
        return IOP_SPLIT_PRESIM_RUNS;
    }

    public double getFOP_SIM_LENGTH() {
        return FOP_SIM_LENGTH.get();
    }

    public DoubleProperty FOP_SIM_LENGTHProperty() {
        return FOP_SIM_LENGTH;
    }

    public double getFOP_SIM_CONFIDENCE() {
        return FOP_SIM_CONFIDENCE.get();
    }

    public DoubleProperty FOP_SIM_CONFIDENCEProperty() {
        return FOP_SIM_CONFIDENCE;
    }

    public double getFOP_SIM_ERROR() {
        return FOP_SIM_ERROR.get();
    }

    public DoubleProperty FOP_SIM_ERRORProperty() {
        return FOP_SIM_ERROR;
    }

}
