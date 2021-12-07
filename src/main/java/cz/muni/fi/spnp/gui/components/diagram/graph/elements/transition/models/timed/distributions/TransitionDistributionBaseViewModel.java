package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class TransitionDistributionBaseViewModel implements TransitionDistributionViewModel {

    private final ObjectProperty<PlaceViewModel> dependentPlace;
    protected List<StringProperty> values;
    protected List<String> valuesNames;
    private final ObjectProperty<TransitionDistributionType> distributionType;
    protected List<ObjectProperty<FunctionViewModel>> functions;

    protected TransitionDistributionBaseViewModel() {
        distributionType = new SimpleObjectProperty<>(TransitionDistributionType.Constant);
        this.dependentPlace = new SimpleObjectProperty<>();
        this.values = createValues();
        this.valuesNames = createValuesNames();
        this.functions = createFunctionsArray();
    }

    public TransitionDistributionBaseViewModel(TransitionDistributionType distributionType, PlaceViewModel dependentPlace) {
        this.distributionType = new SimpleObjectProperty<>(distributionType);
        this.dependentPlace = new SimpleObjectProperty<>(dependentPlace);
        this.values = createValues();
        this.valuesNames = createValuesNames();
        this.functions = createFunctionsArray();
    }

    protected List<StringProperty> createNValues(int count) {
        var valuesList = new ArrayList<StringProperty>();
        for (int i = 0; i < count; i++) {
            valuesList.add(new SimpleStringProperty());
        }
        return valuesList;
    }

    protected List<String> createNValuesNames(int count) {
        var valuesList = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            valuesList.add(String.format("Value %d", i + 1));
        }
        return valuesList;
    }

    protected List<ObjectProperty<FunctionViewModel>> createNFunctions(int count) {
        var functionsList = new ArrayList<ObjectProperty<FunctionViewModel>>();
        for (int i = 0; i < count; i++) {
            functionsList.add(new SimpleObjectProperty<FunctionViewModel>());
        }
        return functionsList;
    }

    protected abstract List<StringProperty> createValues();

    protected abstract List<String> createValuesNames();

    protected abstract List<ObjectProperty<FunctionViewModel>> createFunctionsArray();

    @Override
    public List<StringProperty> getValues() {
        return values;
    }

    @Override
    public List<String> getValuesNames() {
        return valuesNames;
    }

    @Override
    public List<ObjectProperty<FunctionViewModel>> getFunctions() {
        return functions;
    }

    public TransitionDistributionType getDistributionType() {
        return distributionType.get();
    }

    @Override
    public ObjectProperty<TransitionDistributionType> distributionTypeProperty() {
        return this.distributionType;
    }

    @Override
    public ObjectProperty<PlaceViewModel> dependentPlaceProperty() {
        return this.dependentPlace;
    }

    @Override
    public void setDependentPlace(PlaceViewModel dependentPlace) {
        this.dependentPlace.set(dependentPlace);
    }

}
