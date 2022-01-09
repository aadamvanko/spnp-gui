package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

/**
 * View model of the analysis options.
 */
public class AnalysisOptionsViewModel {

    private final ConstantValueOptionViewModel IOP_MC;
    private final ConstantValueOptionViewModel IOP_SSMETHOD;
    private final ConstantValueOptionViewModel IOP_SSDETECT;
    private final DoubleOptionViewModel FOP_SSPRES;
    private final ConstantValueOptionViewModel IOP_TSMETHOD;
    private final ConstantValueOptionViewModel IOP_CUMULATIVE;
    private final ConstantValueOptionViewModel IOP_SENSITIVITY;
    private final IntegerOptionViewModel IOP_ITERATIONS;
    private final DoubleOptionViewModel FOP_PRECISION;

    public AnalysisOptionsViewModel() {
        this.IOP_MC = new ConstantValueOptionViewModel(OptionKey.IOP_MC, VAL_CTMC);
        this.IOP_SSMETHOD = new ConstantValueOptionViewModel(OptionKey.IOP_SSMETHOD, VAL_SSSOR);
        this.IOP_SSDETECT = new ConstantValueOptionViewModel(OptionKey.IOP_SSDETECT, VAL_YES);
        this.FOP_SSPRES = new DoubleOptionViewModel(OptionKey.FOP_SSPRES, 0.25);
        this.IOP_TSMETHOD = new ConstantValueOptionViewModel(OptionKey.IOP_TSMETHOD, VAL_FOXUNIF);
        this.IOP_CUMULATIVE = new ConstantValueOptionViewModel(OptionKey.IOP_CUMULATIVE, VAL_YES);
        this.IOP_SENSITIVITY = new ConstantValueOptionViewModel(OptionKey.IOP_SENSITIVITY, VAL_NO);
        this.IOP_ITERATIONS = new IntegerOptionViewModel(OptionKey.IOP_ITERATIONS, 2000);
        this.FOP_PRECISION = new DoubleOptionViewModel(OptionKey.FOP_PRECISION, 0.000001);
    }

    public ConstantValueOptionViewModel getIOP_MC() {
        return IOP_MC;
    }

    public ConstantValueOptionViewModel getIOP_SSMETHOD() {
        return IOP_SSMETHOD;
    }

    public ConstantValueOptionViewModel getIOP_SSDETECT() {
        return IOP_SSDETECT;
    }

    public DoubleOptionViewModel getFOP_SSPRES() {
        return FOP_SSPRES;
    }

    public ConstantValueOptionViewModel getIOP_TSMETHOD() {
        return IOP_TSMETHOD;
    }

    public ConstantValueOptionViewModel getIOP_CUMULATIVE() {
        return IOP_CUMULATIVE;
    }

    public ConstantValueOptionViewModel getIOP_SENSITIVITY() {
        return IOP_SENSITIVITY;
    }

    public IntegerOptionViewModel getIOP_ITERATIONS() {
        return IOP_ITERATIONS;
    }

    public DoubleOptionViewModel getFOP_PRECISION() {
        return FOP_PRECISION;
    }

}
