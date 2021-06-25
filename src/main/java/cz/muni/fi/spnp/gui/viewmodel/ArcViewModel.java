package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ArcViewModel extends ElementViewModel {

    private final ObjectProperty<ArcMultiplicityType> arcMultiplicityType = new SimpleObjectProperty<>(ArcMultiplicityType.CONSTANT);
    private final StringProperty multiplicity = new SimpleStringProperty("1");
    private final StringProperty multiplicityFunction = new SimpleStringProperty();
    private final ElementViewModel fromViewModel;
    private final ElementViewModel toViewModel;
    private final ObservableList<ArcDragMarkViewModel> dragMarks;

    public ArcViewModel(String name, ElementViewModel fromViewModel, ElementViewModel toViewModel, List<ArcDragMarkViewModel> dragMarks) {
        nameProperty().set(name);

        if (fromViewModel == null) {
            throw new IllegalArgumentException("fromViewModel cannot be null");
        }

        if (toViewModel == null) {
            throw new IllegalArgumentException("toViewModel cannot be null");
        }

        this.fromViewModel = fromViewModel;
        this.toViewModel = toViewModel;
        this.dragMarks = FXCollections.observableArrayList(dragMarks);
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

    public StringProperty multiplicityProperty() {
        return multiplicity;
    }

    public StringProperty multiplicityFunctionProperty() {
        return multiplicityFunction;
    }

    public ObjectProperty<ArcMultiplicityType> multiplicityTypeProperty() {
        return arcMultiplicityType;
    }

    public ObservableList<ArcDragMarkViewModel> getDragMarks() {
        return dragMarks;
    }
}
