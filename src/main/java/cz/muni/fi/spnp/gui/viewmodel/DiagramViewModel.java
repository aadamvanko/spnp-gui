package cz.muni.fi.spnp.gui.viewmodel;

import cz.muni.fi.spnp.core.models.functions.Function;
import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineView;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Arc;

import java.util.List;
import java.util.stream.Collectors;

public class DiagramViewModel extends DisplayableViewModel {

    private final Notifications notifications;
    private final ProjectViewModel projectViewModel;
    private final ObservableList<ElementViewModel> elements;
    private final ObservableList<DefineViewModel> defines;
    private final ObservableList<FunctionViewModel> functions;

    public DiagramViewModel(Notifications notifications, ProjectViewModel projectViewModel) {
        this(notifications, projectViewModel, FXCollections.observableArrayList(), FXCollections.observableArrayList(), FXCollections.observableArrayList());
    }

    public DiagramViewModel(Notifications notifications, ProjectViewModel projectViewModel, List<ElementViewModel> elements, List<DefineViewModel> defines, List<FunctionViewModel> functions) {
        super("unnamedDiagram");
        this.notifications = notifications;
        this.projectViewModel = projectViewModel;
        this.elements = FXCollections.observableArrayList(elements);
        this.defines = FXCollections.observableArrayList(defines);
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

    public ObservableList<DefineViewModel> getDefines() {
        return defines;
    }

    public ObservableList<FunctionViewModel> getFunctions() {
        return functions;
    }

    public ObservableList<ElementViewModel> getElements() {
        return elements;
    }
}
