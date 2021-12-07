package cz.muni.fi.spnp.gui.components.diagram;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.inputparameters.InputParameterViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.variables.VariableViewModel;
import cz.muni.fi.spnp.gui.components.propertieseditor.common.MySimpleIntegerProperty;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * View model of the diagram.
 */
public class DiagramViewModel extends DisplayableViewModel {

    public static final int ZOOM_STEP = 10;
    public static final int ZOOM_MAX_VALUE = 500;
    public static final int ZOOM_MIN_VALUE = 10;

    private final ProjectViewModel projectViewModel;
    private final ObservableList<ElementViewModel> elements;
    private final ObservableList<IncludeViewModel> includes;
    private final ObservableList<DefineViewModel> defines;
    private final ObservableList<VariableViewModel> variables;
    private final ObservableList<InputParameterViewModel> inputParameters;
    private final ObservableList<FunctionViewModel> functions;

    private final IntegerProperty zoomLevel;
    private final BooleanProperty gridSnapping;
    private final ObjectProperty<DiagramViewMode> viewMode;
    private final ObservableList<ElementViewModel> selected;
    private final BooleanProperty needsCodeRefresh;
    private final BooleanProperty showTransitionDetails;

    public DiagramViewModel(ProjectViewModel projectViewModel) {
        this(projectViewModel,
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(new IncludeViewModel("\"user.h\"")),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
    }

    public DiagramViewModel(ProjectViewModel projectViewModel, List<ElementViewModel> elements,
                            List<IncludeViewModel> includes, List<DefineViewModel> defines, List<VariableViewModel> variables,
                            List<InputParameterViewModel> inputParameters, List<FunctionViewModel> functions) {
        nameProperty().set("unnamedDiagram");

        this.projectViewModel = projectViewModel;
        this.includes = FXCollections.observableArrayList(includes);
        this.elements = FXCollections.observableArrayList(elements);
        this.defines = FXCollections.observableArrayList(defines);
        this.variables = FXCollections.observableArrayList(variables);
        this.inputParameters = FXCollections.observableArrayList(inputParameters);
        this.functions = FXCollections.observableArrayList(predefinedFunctions());

        for (var function : functions) {
            addFunction(function);
        }

        zoomLevel = new MySimpleIntegerProperty(120);
        gridSnapping = new SimpleBooleanProperty(true);
        viewMode = new SimpleObjectProperty<>(DiagramViewMode.GRAPH);
        selected = FXCollections.observableArrayList();
        needsCodeRefresh = new SimpleBooleanProperty(false);
        showTransitionDetails = new SimpleBooleanProperty(false);

        this.elements.addListener(this::onElementsChangedListener);
        this.functions.addListener(this::onFunctionsChangedListener);
    }

    private void onElementsChangedListener(ListChangeListener.Change<? extends ElementViewModel> elementsChange) {
        while (elementsChange.next()) {
            elementsChange.getRemoved().forEach(elementViewModel -> {
                if (elementViewModel instanceof PlaceViewModel) {
                    removePlaceReferences((PlaceViewModel) elementViewModel);
                }
            });
        }
    }

    private void removePlaceReferences(PlaceViewModel placeViewModel) {
        elements.forEach(elementViewModel -> elementViewModel.removePlaceReference(placeViewModel));
    }

    private void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        while (functionsChange.next()) {
            functionsChange.getRemoved().forEach(this::removeFunctionReferences);
        }
    }

    private void removeFunctionReferences(FunctionViewModel removedFunction) {
        elements.forEach(elementViewModel -> elementViewModel.removeFunctionReference(removedFunction));
    }

    private void addFunction(FunctionViewModel function) {
        int index = functions.indexOf(function);
        if (index != -1) {
            functions.get(index).bodyProperty().set(function.getBody());
        } else {
            functions.add(function);
        }
    }

    public FunctionViewModel createFlushFunction(String fromPlaceName) {
        int id = 0;
        var flushFunction = new FunctionViewModel(createFlushFunctionName(id),
                FunctionType.ArcCardinality,
                ViewModelUtils.createFlushFunctionBody(fromPlaceName),
                FunctionReturnType.INT,
                false,
                false);
        while (functions.contains(flushFunction)) {
            id++;
            flushFunction.nameProperty().set(createFlushFunctionName(id));
        }
        functions.add(flushFunction);
        return flushFunction;
    }

    private String createFlushFunctionName(int id) {
        return String.format("flush_arc_%d", id);
    }

    public FunctionViewModel getFunctionByName(String functionName) {
        return ViewModelUtils.findFunctionByName(functions, functionName);
    }

    private List<FunctionViewModel> predefinedFunctions() {
        var predefinedFunctions = List.of(
                new FunctionViewModel("assert", FunctionType.Other, "", FunctionReturnType.INT, true, true),
                new FunctionViewModel("ac_init", FunctionType.Other, "/* Information on the net structure */" + System.lineSeparator() + "pr_net_info();", FunctionReturnType.VOID, true, true),
                new FunctionViewModel("ac_reach", FunctionType.Other, "/* Information on the reachability graph */" + System.lineSeparator() + "pr_rg_info();", FunctionReturnType.VOID, true, true),
                new FunctionViewModel("ac_final", FunctionType.Other, "", FunctionReturnType.VOID, true, true)
        );
        return predefinedFunctions;
    }

    public void select(List<ElementViewModel> selectedViewModels) {
        resetSelection();
        selected.addAll(selectedViewModels);
        selected.forEach(viewModel -> viewModel.highlightedProperty().set(true));
    }

    public void resetSelection() {
        selected.forEach(viewModel -> viewModel.highlightedProperty().set(false));
        selected.clear();
    }

    public ObservableList<ElementViewModel> getSelected() {
        return selected;
    }

    public void removeDisconnectedArcs() {
        var arcsToRemove = elements.stream().filter(element -> {
            if (element instanceof ArcViewModel) {
                var arc = (ArcViewModel) element;
                return !elements.contains(arc.getFromViewModel()) || !elements.contains(arc.getToViewModel());
            }
            return false;
        }).collect(Collectors.toList());
        for (var arc : arcsToRemove) {
            ((ArcViewModel) arc).removeFlushFunctionChangeListener();
            elements.remove(arc);
        }
    }

    public void zoomIn() {
        setZoomLevel(Math.min(ZOOM_MAX_VALUE, getZoomLevel() + ZOOM_STEP));
    }

    public void zoomOut() {
        setZoomLevel(Math.max(ZOOM_MIN_VALUE, getZoomLevel() - ZOOM_STEP));
    }

    public int getZoomLevel() {
        return zoomLevel.get();
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel.set(zoomLevel);
    }

    public IntegerProperty zoomLevelProperty() {
        return zoomLevel;
    }

    public DiagramViewMode getViewMode() {
        return viewMode.get();
    }

    public void setViewMode(DiagramViewMode viewMode) {
        this.viewMode.set(viewMode);
    }

    public ObjectProperty<DiagramViewMode> viewModeProperty() {
        return viewMode;
    }

    public boolean isGridSnapping() {
        return gridSnapping.get();
    }

    public BooleanProperty gridSnappingProperty() {
        return gridSnapping;
    }

    public ProjectViewModel getProject() {
        return projectViewModel;
    }

    public boolean needsCodeRefresh() {
        return needsCodeRefresh.get();
    }

    public BooleanProperty needsCodeRefreshProperty() {
        return needsCodeRefresh;
    }

    public boolean isShowTransitionDetails() {
        return showTransitionDetails.get();
    }

    public BooleanProperty showTransitionDetailsProperty() {
        return showTransitionDetails;
    }

    public ObservableList<IncludeViewModel> getIncludes() {
        return includes;
    }

    public ObservableList<DefineViewModel> getDefines() {
        return defines;
    }

    public DefineViewModel getDefineByName(String defineName) {
        return defines.stream()
                .filter(defineViewModel -> defineViewModel.getName().equals(defineName))
                .findAny()
                .orElse(null);
    }

    public ObservableList<VariableViewModel> getVariables() {
        return variables;
    }

    public ObservableList<FunctionViewModel> getFunctions() {
        return functions;
    }

    public ObservableList<ElementViewModel> getElements() {
        return elements;
    }

    public List<PlaceViewModel> getPlaces() {
        return ViewModelUtils.onlyElements(PlaceViewModel.class, elements).collect(Collectors.toList());
    }

    public PlaceViewModel getPlaceByName(String placeName) {
        return getPlaces().stream()
                .filter(placeViewModel -> placeViewModel.getName().equals(placeName))
                .findAny()
                .get();
    }

    public List<TransitionViewModel> getTransitions() {
        return ViewModelUtils.onlyElements(TransitionViewModel.class, elements).collect(Collectors.toList());
    }

    public TransitionViewModel getTransitionByName(String name) {
        return getTransitions().stream()
                .filter(transitionViewModel -> transitionViewModel.getName().equals(name))
                .findAny()
                .get();
    }

    public ObservableList<InputParameterViewModel> getInputParameters() {
        return inputParameters;
    }

    public boolean containsElementNameType(ElementViewModel newElement) {
        return elements.stream()
                .filter(element -> element.getName().equals(newElement.getName()))
                .anyMatch(element -> this.sameTypes(element, newElement));
    }

    private boolean sameTypes(ElementViewModel element, ElementViewModel newElement) {
        var classes = List.of(PlaceViewModel.class, ArcViewModel.class, TransitionViewModel.class);
        return classes.stream()
                .anyMatch(classType -> classType.isInstance(element) && classType.isInstance(newElement));
    }

    public InputParameterViewModel getInputParameterByName(String name) {
        return inputParameters.stream()
                .filter(inputParameterViewModel -> inputParameterViewModel.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public VariableViewModel getVariableByName(String name) {
        return variables.stream()
                .filter(variableViewModel -> variableViewModel.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public boolean isElementNameClassDuplicate(String name, Class<?> elementClass) {
        return elements.stream()
                .filter(elementViewModel -> elementViewModel.getName().equals(name))
                .filter(elementClass::isInstance)
                .count() >= 2;
    }

}
