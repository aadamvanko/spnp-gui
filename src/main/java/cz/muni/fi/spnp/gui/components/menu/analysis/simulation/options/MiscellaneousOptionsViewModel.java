package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View model of the miscellaneous options.
 */
public class MiscellaneousOptionsViewModel {

    private final ConstantValueOptionViewModel IOP_ELIMINATION;
    private final ConstantValueOptionViewModel IOP_OK_ABSMARK;
    private final ConstantValueOptionViewModel IOP_OK_VANLOOP;
    private final ConstantValueOptionViewModel IOP_OK_TRANS_M0;
    private final ConstantValueOptionViewModel IOP_OK_VAN_M0;
    private final DoubleOptionViewModel FOP_ABS_RET_M0;
    private final ConstantValueOptionViewModel IOP_DEBUG;
    private final DoubleOptionViewModel FOP_FLUID_EPSILON;
    private final DoubleOptionViewModel FOP_TIME_EPSILON;

    public MiscellaneousOptionsViewModel() {
        IOP_ELIMINATION = new ConstantValueOptionViewModel(OptionKey.IOP_ELIMINATION, VAL_REDONTHEFLY);
        IOP_OK_ABSMARK = new ConstantValueOptionViewModel(OptionKey.IOP_OK_ABSMARK, VAL_NO);
        IOP_OK_VANLOOP = new ConstantValueOptionViewModel(OptionKey.IOP_OK_VANLOOP, VAL_NO);
        IOP_OK_TRANS_M0 = new ConstantValueOptionViewModel(OptionKey.IOP_OK_TRANS_M0, VAL_YES);
        IOP_OK_VAN_M0 = new ConstantValueOptionViewModel(OptionKey.IOP_OK_VAN_M0, VAL_YES);
        FOP_ABS_RET_M0 = new DoubleOptionViewModel(OptionKey.FOP_ABS_RET_M0, 0.0);
        IOP_DEBUG = new ConstantValueOptionViewModel(OptionKey.IOP_DEBUG, VAL_NO);
        FOP_FLUID_EPSILON = new DoubleOptionViewModel(OptionKey.FOP_FLUID_EPSILON, 0.000001);
        FOP_TIME_EPSILON = new DoubleOptionViewModel(OptionKey.FOP_TIME_EPSILON, 0.000001);
    }

    public ConstantValueOptionViewModel getIOP_ELIMINATION() {
        return IOP_ELIMINATION;
    }

    public ConstantValueOptionViewModel getIOP_OK_ABSMARK() {
        return IOP_OK_ABSMARK;
    }

    public ConstantValueOptionViewModel getIOP_OK_VANLOOP() {
        return IOP_OK_VANLOOP;
    }

    public ConstantValueOptionViewModel getIOP_OK_TRANS_M0() {
        return IOP_OK_TRANS_M0;
    }

    public ConstantValueOptionViewModel getIOP_OK_VAN_M0() {
        return IOP_OK_VAN_M0;
    }

    public DoubleOptionViewModel getFOP_ABS_RET_M0() {
        return FOP_ABS_RET_M0;
    }

    public ConstantValueOptionViewModel getIOP_DEBUG() {
        return IOP_DEBUG;
    }

    public DoubleOptionViewModel getFOP_FLUID_EPSILON() {
        return FOP_FLUID_EPSILON;
    }

    public DoubleOptionViewModel getFOP_TIME_EPSILON() {
        return FOP_TIME_EPSILON;
    }

}
