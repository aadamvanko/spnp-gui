package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ArcViewModel extends ElementViewModel {

    private final ObjectProperty<ArcMultiplicityType> multiplicityType = new SimpleObjectProperty<>(ArcMultiplicityType.CONSTANT);
    private final StringProperty multiplicity = new SimpleStringProperty("1");
    private final ObjectProperty<FunctionViewModel> multiplicityFunction = new SimpleObjectProperty<>();
    private final ElementViewModel fromViewModel;
    private final ElementViewModel toViewModel;
    private final ObservableList<DragPointViewModel> dragPoints;

    public ArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel, List<DragPointViewModel> dragPoints) {
        nameProperty().set(name);

        if (fromViewModel == null) {
            throw new IllegalArgumentException("fromViewModel cannot be null");
        }

        if (toViewModel == null) {
            throw new IllegalArgumentException("toViewModel cannot be null");
        }

        this.fromViewModel = fromViewModel;
        this.toViewModel = toViewModel;
        this.dragPoints = FXCollections.observableArrayList(dragPoints);
    }

    public ArcDirection getArcDirection() {
        if (fromViewModel instanceof PlaceViewModel) {
            return ArcDirection.Input;
        } else {
            return ArcDirection.Output;
        }
    }

    public ArcMultiplicityType getMultiplicityType() {
        return multiplicityType.get();
    }

    public ObjectProperty<ArcMultiplicityType> multiplicityTypeProperty() {
        return multiplicityType;
    }

    public String getMultiplicity() {
        return multiplicity.get();
    }

    public StringProperty multiplicityProperty() {
        return multiplicity;
    }

    public FunctionViewModel getMultiplicityFunction() {
        return multiplicityFunction.get();
    }

    public ObjectProperty<FunctionViewModel> multiplicityFunctionProperty() {
        return multiplicityFunction;
    }

    public ElementViewModel getFromViewModel() {
        return fromViewModel;
    }

    public ElementViewModel getToViewModel() {
        return toViewModel;
    }

    public ObservableList<DragPointViewModel> getDragPoints() {
        return dragPoints;
    }
}
