package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

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
        IOP_PR_RSET = new ConstantValueOptionViewModel(OptionKey.IOP_PR_RSET, VAL_NO);
        IOP_PR_RGRAPH = new ConstantValueOptionViewModel(OptionKey.IOP_PR_RGRAPH, VAL_NO);
        IOP_PR_MARK_ORDER = new ConstantValueOptionViewModel(OptionKey.IOP_PR_MARK_ORDER, VAL_CANONIC);
        IOP_PR_MERG_MARK = new ConstantValueOptionViewModel(OptionKey.IOP_PR_MERG_MARK, VAL_YES);
        IOP_PR_FULL_MARK = new ConstantValueOptionViewModel(OptionKey.IOP_PR_FULL_MARK, VAL_NO);
        IOP_USENAME = new ConstantValueOptionViewModel(OptionKey.IOP_USENAME, VAL_NO);
        IOP_PR_MC = new ConstantValueOptionViewModel(OptionKey.IOP_PR_MC, VAL_NO);
        IOP_PR_DERMC = new ConstantValueOptionViewModel(OptionKey.IOP_PR_DERMC, VAL_NO);
        IOP_PR_MC_ORDER = new ConstantValueOptionViewModel(OptionKey.IOP_PR_MC_ORDER, VAL_FROMTO);
        IOP_PR_PROB = new ConstantValueOptionViewModel(OptionKey.IOP_PR_PROB, VAL_NO);
        IOP_PR_PROBDTMC = new ConstantValueOptionViewModel(OptionKey.IOP_PR_PROBDTMC, VAL_NO);
        IOP_PR_DOT = new ConstantValueOptionViewModel(OptionKey.IOP_PR_DOT, VAL_NO);
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
