package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Base class for all output option view models.
 */
public abstract class OutputOptionViewModel {

    private final StringProperty title;
    private StringProperty time;
    private ObjectProperty<PlaceViewModel> place;
    private ObjectProperty<TransitionViewModel> transition;
    private ObjectProperty<VariableViewModel> variable;
    private ObjectProperty<FunctionViewModel> function;

    public OutputOptionViewModel(String title) {
        this.title = new SimpleStringProperty(title);
    }

    protected void initTime() {
        time = new SimpleStringProperty("");
    }

    protected void initPlace() {
        place = new SimpleObjectProperty<>();
    }

    protected void initTransition() {
        transition = new SimpleObjectProperty<>();
    }

    protected void initVariable() {
        variable = new SimpleObjectProperty<>();
    }

    protected void initFunction() {
        function = new SimpleObjectProperty<>();
    }

    public abstract OutputOptionViewModel cleanCopy();

    public void copyTo(OutputOptionViewModel copy) {
        copy.title.set(getTitle());
        if (time != null) copy.time = new SimpleStringProperty(getTime());
        if (place != null) copy.place = new SimpleObjectProperty<>(getPlace());
        if (transition != null) copy.transition = new SimpleObjectProperty<>(getTransition());
        if (variable != null) copy.variable = new SimpleObjectProperty<>(getVariable());
        if (function != null) copy.function = new SimpleObjectProperty<>(getFunction());
    }

    public void reset() {
        if (time != null) time.set("");
        if (place != null) place.set(null);
        if (transition != null) transition.set(null);
        if (variable != null) variable.set(null);
        if (function != null) function.set(null);
    }

    public abstract void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel);

    protected FunctionViewModel addPlaceTokensFunction(OutputOptionsResult result, DiagramViewModel diagramViewModel, PlaceViewModel placeViewModel) {
        var outFunc = new FunctionViewModel("outFunc", FunctionType.Other, String.format("return mark(\"%s\");", placeViewModel.getName()),
                FunctionReturnType.DOUBLE, false, true);
        return renameIfNeeded(result, diagramViewModel, outFunc);
    }

    protected FunctionViewModel addTransitionRateFunction(OutputOptionsResult result, DiagramViewModel diagramViewModel, TransitionViewModel transition) {
        var outFunc = new FunctionViewModel("outFunc", FunctionType.Other, String.format("return rate(\"%s\");", transition.getName()),
                FunctionReturnType.DOUBLE, false, true);
        return renameIfNeeded(result, diagramViewModel, outFunc);
    }

    protected FunctionViewModel addTransitionEnabledFunction(OutputOptionsResult result, DiagramViewModel diagramViewModel, TransitionViewModel transition) {
        var outFunc = new FunctionViewModel("outFunc", FunctionType.Other, String.format("return enabled(\"%s\");", transition.getName()),
                FunctionReturnType.DOUBLE, false, true);
        return renameIfNeeded(result, diagramViewModel, outFunc);
    }

    protected FunctionViewModel addPlaceIsEmptyFunction(OutputOptionsResult result, DiagramViewModel diagramViewModel, PlaceViewModel placeViewModel) {
        var outFunc = new FunctionViewModel("outFunc", FunctionType.Other, String.format("return mark(\"%s\") == 0 ? 1.0 : 0.0;", placeViewModel.getName()),
                FunctionReturnType.DOUBLE, false, true);
        return renameIfNeeded(result, diagramViewModel, outFunc);
    }

    private FunctionViewModel renameIfNeeded(OutputOptionsResult result, DiagramViewModel diagramViewModel, FunctionViewModel outFunc) {
        int id = 0;
        var oldName = outFunc.getName();
        outFunc.nameProperty().set(oldName + id);
        while (diagramViewModel.getFunctionByName(outFunc.getName()) != null ||
                ViewModelUtils.findFunctionByName(result.getFunctions(), outFunc.getName()) != null) {
            id++;
            outFunc.nameProperty().set(oldName + id);
        }
        result.getFunctions().add(outFunc);
        return outFunc;
    }

    protected void addSolve(OutputOptionsResult result) {
        result.getLines().add("solve(INFINITY);");
    }

    protected void addSolve(OutputOptionsResult result, String time) {
        result.getLines().add(String.format("solve((double) %s);", time));
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public PlaceViewModel getPlace() {
        return place.get();
    }

    public ObjectProperty<PlaceViewModel> placeProperty() {
        return place;
    }

    public TransitionViewModel getTransition() {
        return transition.get();
    }

    public ObjectProperty<TransitionViewModel> transitionProperty() {
        return transition;
    }

    public VariableViewModel getVariable() {
        return variable.get();
    }

    public ObjectProperty<VariableViewModel> variableProperty() {
        return variable;
    }

    public FunctionViewModel getFunction() {
        return function.get();
    }

    public ObjectProperty<FunctionViewModel> functionProperty() {
        return function;
    }

    @Override
    public String toString() {
        return title.get();
    }
}
