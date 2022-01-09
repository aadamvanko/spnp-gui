package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

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
        this.IOP_SIMULATION = new ConstantValueOptionViewModel(OptionKey.IOP_SIMULATION, VAL_YES);
        this.IOP_SIM_RUNS = new IntegerOptionViewModel(OptionKey.IOP_SIM_RUNS, 0);
        this.IOP_SIM_RUNMETHOD = new ConstantValueOptionViewModel(OptionKey.IOP_SIM_RUNMETHOD, VAL_REPL);
        this.IOP_SIM_SEED = new IntegerOptionViewModel(OptionKey.IOP_SIM_SEED, 52836);
        this.IOP_SIM_CUMULATIVE = new ConstantValueOptionViewModel(OptionKey.IOP_SIM_CUMULATIVE, VAL_YES);
        this.IOP_SIM_STD_REPORT = new ConstantValueOptionViewModel(OptionKey.IOP_SIM_STD_REPORT, VAL_YES);
        this.IOP_SPLIT_LEVEL_DOWN = new IntegerOptionViewModel(OptionKey.IOP_SPLIT_LEVEL_DOWN, 60);
        this.IOP_SPLIT_PRESIM = new ConstantValueOptionViewModel(OptionKey.IOP_SPLIT_PRESIM, VAL_YES);
        this.IOP_SPLIT_NUMBER = new IntegerOptionViewModel(OptionKey.IOP_SPLIT_NUMBER, 6);
        this.IOP_SPLIT_RESTART_FINISH = new ConstantValueOptionViewModel(OptionKey.IOP_SPLIT_RESTART_FINISH, VAL_NO);
        this.IOP_SPLIT_PRESIM_RUNS = new IntegerOptionViewModel(OptionKey.IOP_SPLIT_PRESIM_RUNS, 10);
        this.FOP_SIM_LENGTH = new DoubleOptionViewModel(OptionKey.FOP_SIM_LENGTH, 0.0);
        this.FOP_SIM_CONFIDENCE = new DoubleOptionViewModel(OptionKey.FOP_SIM_CONFIDENCE, 0.95);
        this.FOP_SIM_ERROR = new DoubleOptionViewModel(OptionKey.FOP_SIM_ERROR, 0.1);
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
