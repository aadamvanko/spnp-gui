package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View model of the simulation options.
 */
public class SimulationOptionsViewModel {

    private final ConstantValueOptionViewModel IOP_SIMULATION;
    private final IntegerOptionViewModel IOP_SIM_RUNS;
    private final ConstantValueOptionViewModel IOP_SIM_RUNMETHOD;
    private final IntegerOptionViewModel IOP_SIM_SEED;
    private final ConstantValueOptionViewModel IOP_SIM_CUMULATIVE;
    private final ConstantValueOptionViewModel IOP_SIM_STD_REPORT;
    private final IntegerOptionViewModel IOP_SPLIT_LEVEL_DOWN;
    private final ConstantValueOptionViewModel IOP_SPLIT_PRESIM;
    private final IntegerOptionViewModel IOP_SPLIT_NUMBER;
    private final ConstantValueOptionViewModel IOP_SPLIT_RESTART_FINISH;
    private final IntegerOptionViewModel IOP_SPLIT_PRESIM_RUNS;
    private final DoubleOptionViewModel FOP_SIM_LENGTH;
    private final DoubleOptionViewModel FOP_SIM_CONFIDENCE;
    private final DoubleOptionViewModel FOP_SIM_ERROR;

    public SimulationOptionsViewModel() {
        this.IOP_SIMULATION = new ConstantValueOptionViewModel(VAL_YES);
        this.IOP_SIM_RUNS = new IntegerOptionViewModel(0);
        this.IOP_SIM_RUNMETHOD = new ConstantValueOptionViewModel(VAL_REPL);
        this.IOP_SIM_SEED = new IntegerOptionViewModel(52836);
        this.IOP_SIM_CUMULATIVE = new ConstantValueOptionViewModel(VAL_YES);
        this.IOP_SIM_STD_REPORT = new ConstantValueOptionViewModel(VAL_YES);
        this.IOP_SPLIT_LEVEL_DOWN = new IntegerOptionViewModel(60);
        this.IOP_SPLIT_PRESIM = new ConstantValueOptionViewModel(VAL_YES);
        this.IOP_SPLIT_NUMBER = new IntegerOptionViewModel(6);
        this.IOP_SPLIT_RESTART_FINISH = new ConstantValueOptionViewModel(VAL_NO);
        this.IOP_SPLIT_PRESIM_RUNS = new IntegerOptionViewModel(10);
        this.FOP_SIM_LENGTH = new DoubleOptionViewModel(0.0);
        this.FOP_SIM_CONFIDENCE = new DoubleOptionViewModel(0.95);
        this.FOP_SIM_ERROR = new DoubleOptionViewModel(0.1);
    }

    public ConstantValueOptionViewModel getIOP_SIMULATION() {
        return IOP_SIMULATION;
    }

    public IntegerOptionViewModel getIOP_SIM_RUNS() {
        return IOP_SIM_RUNS;
    }

    public ConstantValueOptionViewModel getIOP_SIM_RUNMETHOD() {
        return IOP_SIM_RUNMETHOD;
    }

    public IntegerOptionViewModel getIOP_SIM_SEED() {
        return IOP_SIM_SEED;
    }

    public ConstantValueOptionViewModel getIOP_SIM_CUMULATIVE() {
        return IOP_SIM_CUMULATIVE;
    }

    public ConstantValueOptionViewModel getIOP_SIM_STD_REPORT() {
        return IOP_SIM_STD_REPORT;
    }

    public IntegerOptionViewModel getIOP_SPLIT_LEVEL_DOWN() {
        return IOP_SPLIT_LEVEL_DOWN;
    }

    public ConstantValueOptionViewModel getIOP_SPLIT_PRESIM() {
        return IOP_SPLIT_PRESIM;
    }

    public IntegerOptionViewModel getIOP_SPLIT_NUMBER() {
        return IOP_SPLIT_NUMBER;
    }

    public ConstantValueOptionViewModel getIOP_SPLIT_RESTART_FINISH() {
        return IOP_SPLIT_RESTART_FINISH;
    }

    public IntegerOptionViewModel getIOP_SPLIT_PRESIM_RUNS() {
        return IOP_SPLIT_PRESIM_RUNS;
    }

    public DoubleOptionViewModel getFOP_SIM_LENGTH() {
        return FOP_SIM_LENGTH;
    }

    public DoubleOptionViewModel getFOP_SIM_CONFIDENCE() {
        return FOP_SIM_CONFIDENCE;
    }

    public DoubleOptionViewModel getFOP_SIM_ERROR() {
        return FOP_SIM_ERROR;
    }

}
