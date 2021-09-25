package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.propertieseditor.MySimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

public class MiscellaneousOptionsViewModel {

    private final ObjectProperty<ConstantValue> IOP_ELIMINATION;
    private final ObjectProperty<ConstantValue> IOP_OK_ABSMARK;
    private final ObjectProperty<ConstantValue> IOP_OK_VANLOOP;
    private final ObjectProperty<ConstantValue> IOP_OK_TRANS_M0;
    private final ObjectProperty<ConstantValue> IOP_OK_VAN_M0;
    private final DoubleProperty FOP_ABS_RET_M0;
    private final ObjectProperty<ConstantValue> IOP_DEBUG;
    private final DoubleProperty FOP_FLUID_EPSILON;
    private final DoubleProperty FOP_TIME_EPSILON;

    public MiscellaneousOptionsViewModel() {
        IOP_ELIMINATION = new SimpleObjectProperty<>(VAL_REDONTHEFLY);
        IOP_OK_ABSMARK = new SimpleObjectProperty<>(VAL_NO);
        IOP_OK_VANLOOP = new SimpleObjectProperty<>(VAL_NO);
        IOP_OK_TRANS_M0 = new SimpleObjectProperty<>(VAL_YES);
        IOP_OK_VAN_M0 = new SimpleObjectProperty<>(VAL_YES);
        FOP_ABS_RET_M0 = new MySimpleDoubleProperty(0.0);
        IOP_DEBUG = new SimpleObjectProperty<>(VAL_NO);
        FOP_FLUID_EPSILON = new MySimpleDoubleProperty(0.000001);
        FOP_TIME_EPSILON = new MySimpleDoubleProperty(0.000001);
    }

    public ConstantValue getIOP_ELIMINATION() {
        return IOP_ELIMINATION.get();
    }

    public ObjectProperty<ConstantValue> IOP_ELIMINATIONProperty() {
        return IOP_ELIMINATION;
    }

    public ConstantValue getIOP_OK_ABSMARK() {
        return IOP_OK_ABSMARK.get();
    }

    public ObjectProperty<ConstantValue> IOP_OK_ABSMARKProperty() {
        return IOP_OK_ABSMARK;
    }

    public ConstantValue getIOP_OK_VANLOOP() {
        return IOP_OK_VANLOOP.get();
    }

    public ObjectProperty<ConstantValue> IOP_OK_VANLOOPProperty() {
        return IOP_OK_VANLOOP;
    }

    public ConstantValue getIOP_OK_TRANS_M0() {
        return IOP_OK_TRANS_M0.get();
    }

    public ObjectProperty<ConstantValue> IOP_OK_TRANS_M0Property() {
        return IOP_OK_TRANS_M0;
    }

    public ConstantValue getIOP_OK_VAN_M0() {
        return IOP_OK_VAN_M0.get();
    }

    public ObjectProperty<ConstantValue> IOP_OK_VAN_M0Property() {
        return IOP_OK_VAN_M0;
    }

    public double getFOP_ABS_RET_M0() {
        return FOP_ABS_RET_M0.get();
    }

    public DoubleProperty FOP_ABS_RET_M0Property() {
        return FOP_ABS_RET_M0;
    }

    public ConstantValue getIOP_DEBUG() {
        return IOP_DEBUG.get();
    }

    public ObjectProperty<ConstantValue> IOP_DEBUGProperty() {
        return IOP_DEBUG;
    }

    public double getFOP_FLUID_EPSILON() {
        return FOP_FLUID_EPSILON.get();
    }

    public DoubleProperty FOP_FLUID_EPSILONProperty() {
        return FOP_FLUID_EPSILON;
    }

    public double getFOP_TIME_EPSILON() {
        return FOP_TIME_EPSILON.get();
    }

    public DoubleProperty FOP_TIME_EPSILONProperty() {
        return FOP_TIME_EPSILON;
    }

}
