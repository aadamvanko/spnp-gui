package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ArcViewModel extends ElementViewModel {

    private final IntegerProperty multiplicity = new SimpleIntegerProperty();
    private final StringProperty multiplicityFunction = new SimpleStringProperty();
    private final ElementViewModel fromViewModel;
    private final ElementViewModel toViewModel;

    public ArcViewModel(ElementViewModel fromViewModel, ElementViewModel toViewModel) {
        this.fromViewModel = fromViewModel;
        this.toViewModel = toViewModel;
    }

    public ArcDirection getArcDirection() {
        if (fromViewModel instanceof PlaceViewModel) {
            return ArcDirection.Input;
        } else {
            return ArcDirection.Output;
        }
    }

    public IntegerProperty multiplicityProperty() {
        return multiplicity;
    }

    public StringProperty multiplicityFunctionProperty() {
        return multiplicityFunction;
    }
}
