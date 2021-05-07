package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import javafx.beans.property.*;

public class ArcViewModel extends ElementViewModel {

    private final ObjectProperty<ArcMultiplicityType> arcMultiplicityType = new SimpleObjectProperty<>(ArcMultiplicityType.CONSTANT);
    private final IntegerProperty multiplicity = new SimpleIntegerProperty(1);
    private final StringProperty multiplicityFunction = new SimpleStringProperty();
    private final ElementViewModel fromViewModel;
    private final ElementViewModel toViewModel;

    public ArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel) {
        super(name);

        if (fromViewModel == null) {
            throw new IllegalArgumentException("fromViewModel cannot be null");
        }

        if (toViewModel == null) {
            throw new IllegalArgumentException("toViewModel cannot be null");
        }

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

    public ElementViewModel getFromViewModel() {
        return fromViewModel;
    }

    public ElementViewModel getToViewModel() {
        return toViewModel;
    }

    public IntegerProperty multiplicityProperty() {
        return multiplicity;
    }

    public StringProperty multiplicityFunctionProperty() {
        return multiplicityFunction;
    }

    public ObjectProperty<ArcMultiplicityType> multiplicityTypeProperty() {
        return arcMultiplicityType;
    }
}
