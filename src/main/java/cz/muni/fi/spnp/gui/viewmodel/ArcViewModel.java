package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class ArcViewModel extends ElementViewModel {

    private final ObjectProperty<ArcMultiplicityType> multiplicityType = new SimpleObjectProperty<>(ArcMultiplicityType.Constant);
    private final StringProperty multiplicity = new SimpleStringProperty("1");
    private final ObjectProperty<FunctionViewModel> multiplicityFunction = new SimpleObjectProperty<>();
    private ConnectableViewModel fromViewModel;
    private ConnectableViewModel toViewModel;
    private final ObservableList<DragPointViewModel> dragPoints;

    public ArcViewModel() {
        nameProperty().set("unnamedArcViewModel");
        fromViewModel = null;
        toViewModel = null;
        dragPoints = FXCollections.observableArrayList();
    }

    public ArcViewModel(String name, ConnectableViewModel fromViewModel, ConnectableViewModel toViewModel, List<DragPointViewModel> dragPoints) {
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

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (getMultiplicityFunction() == removedFunction) {
            multiplicityFunctionProperty().set(null);
        }
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

    public ConnectableViewModel getFromViewModel() {
        return fromViewModel;
    }

    public void setFromViewModel(ConnectableViewModel fromViewModel) {
        this.fromViewModel = fromViewModel;
    }

    public ConnectableViewModel getToViewModel() {
        return toViewModel;
    }

    public void setToViewModel(ConnectableViewModel toViewModel) {
        this.toViewModel = toViewModel;
    }

    public ObservableList<DragPointViewModel> getDragPoints() {
        return dragPoints;
    }

}
