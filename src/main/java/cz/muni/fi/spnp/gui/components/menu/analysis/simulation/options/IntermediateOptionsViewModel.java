package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

public class IntermediateOptionsViewModel {

    private final ObjectProperty<ConstantValue> IOP_PR_RSET;
    private final ObjectProperty<ConstantValue> IOP_PR_RGRAPH;
    private final ObjectProperty<ConstantValue> IOP_PR_MARK_ORDER;
    private final ObjectProperty<ConstantValue> IOP_PR_MERG_MARK;
    private final ObjectProperty<ConstantValue> IOP_PR_FULL_MARK;
    private final ObjectProperty<ConstantValue> IOP_USENAME;
    private final ObjectProperty<ConstantValue> IOP_PR_MC;
    private final ObjectProperty<ConstantValue> IOP_PR_DERMC;
    private final ObjectProperty<ConstantValue> IOP_PR_MC_ORDER;
    private final ObjectProperty<ConstantValue> IOP_PR_PROB;
    private final ObjectProperty<ConstantValue> IOP_PR_PROBDTMC;
    private final ObjectProperty<ConstantValue> IOP_PR_DOT;

    public IntermediateOptionsViewModel() {
        IOP_PR_RSET = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_RGRAPH = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_MARK_ORDER = new SimpleObjectProperty<>(VAL_CANONIC);
        IOP_PR_MERG_MARK = new SimpleObjectProperty<>(VAL_YES);
        IOP_PR_FULL_MARK = new SimpleObjectProperty<>(VAL_NO);
        IOP_USENAME = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_MC = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_DERMC = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_MC_ORDER = new SimpleObjectProperty<>(VAL_FROMTO);
        IOP_PR_PROB = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_PROBDTMC = new SimpleObjectProperty<>(VAL_NO);
        IOP_PR_DOT = new SimpleObjectProperty<>(VAL_NO);
    }

    public ConstantValue getIOP_PR_RSET() {
        return IOP_PR_RSET.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_RSETProperty() {
        return IOP_PR_RSET;
    }

    public ConstantValue getIOP_PR_RGRAPH() {
        return IOP_PR_RGRAPH.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_RGRAPHProperty() {
        return IOP_PR_RGRAPH;
    }

    public ConstantValue getIOP_PR_MARK_ORDER() {
        return IOP_PR_MARK_ORDER.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_MARK_ORDERProperty() {
        return IOP_PR_MARK_ORDER;
    }

    public ConstantValue getIOP_PR_MERG_MARK() {
        return IOP_PR_MERG_MARK.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_MERG_MARKProperty() {
        return IOP_PR_MERG_MARK;
    }

    public ConstantValue getIOP_PR_FULL_MARK() {
        return IOP_PR_FULL_MARK.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_FULL_MARKProperty() {
        return IOP_PR_FULL_MARK;
    }

    public ConstantValue getIOP_USENAME() {
        return IOP_USENAME.get();
    }

    public ObjectProperty<ConstantValue> IOP_USENAMEProperty() {
        return IOP_USENAME;
    }

    public ConstantValue getIOP_PR_MC() {
        return IOP_PR_MC.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_MCProperty() {
        return IOP_PR_MC;
    }

    public ConstantValue getIOP_PR_DERMC() {
        return IOP_PR_DERMC.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_DERMCProperty() {
        return IOP_PR_DERMC;
    }

    public ConstantValue getIOP_PR_MC_ORDER() {
        return IOP_PR_MC_ORDER.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_MC_ORDERProperty() {
        return IOP_PR_MC_ORDER;
    }

    public ConstantValue getIOP_PR_PROB() {
        return IOP_PR_PROB.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_PROBProperty() {
        return IOP_PR_PROB;
    }

    public ConstantValue getIOP_PR_PROBDTMC() {
        return IOP_PR_PROBDTMC.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_PROBDTMCProperty() {
        return IOP_PR_PROBDTMC;
    }

    public ConstantValue getIOP_PR_DOT() {
        return IOP_PR_DOT.get();
    }

    public ObjectProperty<ConstantValue> IOP_PR_DOTProperty() {
        return IOP_PR_DOT;
    }

}
