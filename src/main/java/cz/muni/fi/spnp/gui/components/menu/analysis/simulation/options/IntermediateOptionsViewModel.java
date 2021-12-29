package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View model of the intermediate options.
 */
public class IntermediateOptionsViewModel {

    private final ConstantValueOptionViewModel IOP_PR_RSET;
    private final ConstantValueOptionViewModel IOP_PR_RGRAPH;
    private final ConstantValueOptionViewModel IOP_PR_MARK_ORDER;
    private final ConstantValueOptionViewModel IOP_PR_MERG_MARK;
    private final ConstantValueOptionViewModel IOP_PR_FULL_MARK;
    private final ConstantValueOptionViewModel IOP_USENAME;
    private final ConstantValueOptionViewModel IOP_PR_MC;
    private final ConstantValueOptionViewModel IOP_PR_DERMC;
    private final ConstantValueOptionViewModel IOP_PR_MC_ORDER;
    private final ConstantValueOptionViewModel IOP_PR_PROB;
    private final ConstantValueOptionViewModel IOP_PR_PROBDTMC;
    private final ConstantValueOptionViewModel IOP_PR_DOT;

    public IntermediateOptionsViewModel() {
        IOP_PR_RSET = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_RGRAPH = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_MARK_ORDER = new ConstantValueOptionViewModel(VAL_CANONIC);
        IOP_PR_MERG_MARK = new ConstantValueOptionViewModel(VAL_YES);
        IOP_PR_FULL_MARK = new ConstantValueOptionViewModel(VAL_NO);
        IOP_USENAME = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_MC = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_DERMC = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_MC_ORDER = new ConstantValueOptionViewModel(VAL_FROMTO);
        IOP_PR_PROB = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_PROBDTMC = new ConstantValueOptionViewModel(VAL_NO);
        IOP_PR_DOT = new ConstantValueOptionViewModel(VAL_NO);
    }

    public ConstantValueOptionViewModel getIOP_PR_RSET() {
        return IOP_PR_RSET;
    }

    public ConstantValueOptionViewModel getIOP_PR_RGRAPH() {
        return IOP_PR_RGRAPH;
    }

    public ConstantValueOptionViewModel getIOP_PR_MARK_ORDER() {
        return IOP_PR_MARK_ORDER;
    }

    public ConstantValueOptionViewModel getIOP_PR_MERG_MARK() {
        return IOP_PR_MERG_MARK;
    }

    public ConstantValueOptionViewModel getIOP_PR_FULL_MARK() {
        return IOP_PR_FULL_MARK;
    }

    public ConstantValueOptionViewModel getIOP_USENAME() {
        return IOP_USENAME;
    }

    public ConstantValueOptionViewModel getIOP_PR_MC() {
        return IOP_PR_MC;
    }

    public ConstantValueOptionViewModel getIOP_PR_DERMC() {
        return IOP_PR_DERMC;
    }

    public ConstantValueOptionViewModel getIOP_PR_MC_ORDER() {
        return IOP_PR_MC_ORDER;
    }

    public ConstantValueOptionViewModel getIOP_PR_PROB() {
        return IOP_PR_PROB;
    }

    public ConstantValueOptionViewModel getIOP_PR_PROBDTMC() {
        return IOP_PR_PROBDTMC;
    }

    public ConstantValueOptionViewModel getIOP_PR_DOT() {
        return IOP_PR_DOT;
    }

}
