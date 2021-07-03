package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.notifications.Notifications;
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
        this.functions = FXCollections.observableArrayList(functions);
    }

    public void addElement(ElementViewModel elementViewModel) {
        elements.add(elementViewModel);
        notifications.newElementAdded(elementViewModel);
    }

    public void removeElement(ElementViewModel elementViewModel) {
        elements.remove(elementViewModel);
        notifications.elementRemoved(elementViewModel);
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
            removeElement(arc);
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

}
