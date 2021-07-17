package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class DiagramViewModel extends DisplayableViewModel {

    private final Notifications notifications;
    private final ProjectViewModel projectViewModel;
    private final ObservableList<ElementViewModel> elements;
    private final ObservableList<IncludeViewModel> includes;
    private final ObservableList<DefineViewModel> defines;
    private final ObservableList<VariableViewModel> variables;
    private final ObservableList<InputParameterViewModel> inputParameters;
    private final ObservableList<FunctionViewModel> functions;

    public DiagramViewModel(Notifications notifications, ProjectViewModel projectViewModel) {
        this(notifications,
                projectViewModel,
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
    }

    public DiagramViewModel(Notifications notifications, ProjectViewModel projectViewModel, List<ElementViewModel> elements,
                            List<IncludeViewModel> includes, List<DefineViewModel> defines, List<VariableViewModel> variables,
                            List<InputParameterViewModel> inputParameters, List<FunctionViewModel> functions) {
        nameProperty().set("unnamedDiagram");
        this.notifications = notifications;
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
    }

    public void addFunction(FunctionViewModel function) {
        int index = functions.indexOf(function);
        if (index != -1) {
            functions.get(index).bodyProperty().set(function.getBody());
        } else {
            functions.add(function);
        }
    }

    public FunctionViewModel getFunctionByName(String functionName) {
        return functions.stream()
                .filter(function -> function.getName().equals(functionName))
                .findAny()
                .get();
    }

    private List<FunctionViewModel> predefinedFunctions() {
        var predefinedFunctions = List.of(
                new FunctionViewModel("assert", FunctionType.Other, "", FunctionReturnType.INT, true),
                new FunctionViewModel("ac_init", FunctionType.Other, "/* Information on the net structure */" + System.lineSeparator() + "pr_net_info();", FunctionReturnType.VOID, true),
                new FunctionViewModel("ac_reach", FunctionType.Other, "/* Information on the reachability graph */" + System.lineSeparator() + "pr_rg_info();", FunctionReturnType.VOID, true),
                new FunctionViewModel("ac_final", FunctionType.Other, "", FunctionReturnType.VOID, true)
        );
        return predefinedFunctions;
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
            elements.remove(arc);
        }
    }

    public ProjectViewModel getProject() {
        return projectViewModel;
    }

    public ObservableList<IncludeViewModel> getIncludes() {
        return includes;
    }

    public ObservableList<DefineViewModel> getDefines() {
        return defines;
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
}
