package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import cz.muni.fi.spnp.gui.components.diagram.graph.canvas.VoidFunction;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ConnectableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * View model for the arc containing all the necessary data.
 */
public abstract class ArcViewModel extends ElementViewModel {

    private VoidFunction removeStraightLinesCallback;
    private final ObjectProperty<ArcMultiplicityType> multiplicityType = new SimpleObjectProperty<>(ArcMultiplicityType.Constant);
    private final StringProperty multiplicity = new SimpleStringProperty("1");
    private final ObjectProperty<FunctionViewModel> multiplicityFunction = new SimpleObjectProperty<>();
    private ConnectableViewModel fromViewModel;
    private ConnectableViewModel toViewModel;
    private final ObservableList<DragPointViewModel> dragPoints;
    private final BooleanProperty isFlushing = new SimpleBooleanProperty(false);

    private final ChangeListener<String> onFromPlaceNameChangedListener;

    public ArcViewModel() {
        this("unnamedArcViewModel", null, null, FXCollections.observableArrayList());
    }

    public ArcViewModel(String name, ConnectableViewModel fromViewModel, ConnectableViewModel toViewModel, List<DragPointViewModel> dragPoints) {
        nameProperty().set(name);

        this.fromViewModel = fromViewModel;
        this.toViewModel = toViewModel;
        this.dragPoints = FXCollections.observableArrayList(dragPoints);

        this.onFromPlaceNameChangedListener = this::onFromPlaceNameChangedListener;

        addFlushFunctionChangeListener();
    }

    private void onFromPlaceNameChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (isFlushing()) {
            getMultiplicityFunction().bodyProperty().set(ViewModelUtils.createFlushFunctionBody(newValue));
        }
    }

    private void addFlushFunctionChangeListener() {
        if (fromViewModel == null) {
            return;
        }
        fromViewModel.nameProperty().addListener(this.onFromPlaceNameChangedListener);
    }

    public void removeFlushFunctionChangeListener() {
        if (fromViewModel == null) {
            return;
        }
        fromViewModel.nameProperty().removeListener(this.onFromPlaceNameChangedListener);
    }

    @Override
    public void removeFunctionReference(FunctionViewModel removedFunction) {
        super.removeFunctionReference(removedFunction);

        if (getMultiplicityFunction() == removedFunction) {
            multiplicityFunctionProperty().set(null);
        }
    }

    public VoidFunction getRemoveStraightLinesCallback() {
        return removeStraightLinesCallback;
    }

    public void setRemoveStraightLinesCallback(VoidFunction removeStraightLinesCallback) {
        this.removeStraightLinesCallback = removeStraightLinesCallback;
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
        removeFlushFunctionChangeListener();
        this.fromViewModel = fromViewModel;
        addFlushFunctionChangeListener();
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

    public boolean isFlushing() {
        return isFlushing.get();
    }

    public BooleanProperty isFlushingProperty() {
        return isFlushing;
    }

}
