package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleDoubleProperty;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import static cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue.*;

public class AnalysisOptionsViewModel {

    private final ObjectProperty<ConstantValue> IOP_MC;
    private final ObjectProperty<ConstantValue> IOP_SSMETHOD;
    private final ObjectProperty<ConstantValue> IOP_SSDETECT;
    private final DoubleProperty FOP_SSPRES;
    private final ObjectProperty<ConstantValue> IOP_TSMETHOD;
    private final ObjectProperty<ConstantValue> IOP_CUMULATIVE;
    private final ObjectProperty<ConstantValue> IOP_SENSITIVITY;
    private final IntegerProperty IOP_ITERATIONS;
    private final DoubleProperty FOP_PRECISION;

    public AnalysisOptionsViewModel() {
        this.IOP_MC = new SimpleObjectProperty<>(VAL_CTMC);
        this.IOP_SSMETHOD = new SimpleObjectProperty<>(VAL_SSSOR);
        this.IOP_SSDETECT = new SimpleObjectProperty<>(VAL_YES);
        this.FOP_SSPRES = new MySimpleDoubleProperty(0.25);
        this.IOP_TSMETHOD = new SimpleObjectProperty<>(VAL_FOXUNIF);
        this.IOP_CUMULATIVE = new SimpleObjectProperty<>(VAL_YES);
        this.IOP_SENSITIVITY = new SimpleObjectProperty<>(VAL_NO);
        this.IOP_ITERATIONS = new MySimpleIntegerProperty(2000);
        this.FOP_PRECISION = new MySimpleDoubleProperty(0.000001);
    }

    public ConstantValue getIOP_MC() {
        return IOP_MC.get();
    }

    public ObjectProperty<ConstantValue> IOP_MCProperty() {
        return IOP_MC;
    }

    public ConstantValue getIOP_SSMETHOD() {
        return IOP_SSMETHOD.get();
    }

    public ObjectProperty<ConstantValue> IOP_SSMETHODProperty() {
        return IOP_SSMETHOD;
    }

    public ConstantValue getIOP_SSDETECT() {
        return IOP_SSDETECT.get();
    }

    public ObjectProperty<ConstantValue> IOP_SSDETECTProperty() {
        return IOP_SSDETECT;
    }

    public double getFOP_SSPRES() {
        return FOP_SSPRES.get();
    }

    public DoubleProperty FOP_SSPRESProperty() {
        return FOP_SSPRES;
    }

    public ConstantValue getIOP_TSMETHOD() {
        return IOP_TSMETHOD.get();
    }

    public ObjectProperty<ConstantValue> IOP_TSMETHODProperty() {
        return IOP_TSMETHOD;
    }

    public ConstantValue getIOP_CUMULATIVE() {
        return IOP_CUMULATIVE.get();
    }

    public ObjectProperty<ConstantValue> IOP_CUMULATIVEProperty() {
        return IOP_CUMULATIVE;
    }

    public ConstantValue getIOP_SENSITIVITY() {
        return IOP_SENSITIVITY.get();
    }

    public ObjectProperty<ConstantValue> IOP_SENSITIVITYProperty() {
        return IOP_SENSITIVITY;
    }

    public int getIOP_ITERATIONS() {
        return IOP_ITERATIONS.get();
    }

    public IntegerProperty IOP_ITERATIONSProperty() {
        return IOP_ITERATIONS;
    }

    public double getFOP_PRECISION() {
        return FOP_PRECISION.get();
    }

    public DoubleProperty FOP_PRECISIONProperty() {
        return FOP_PRECISION;
    }
}
