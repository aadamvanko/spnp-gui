package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

public class DiagramComponent extends ApplicationComponent {

    private TabPane tabPane;
    private Map<Tab, DiagramView> diagramViews;
    private final ListChangeListener<? super DiagramViewModel> onDiagramsChangedListener;

    public DiagramComponent(Model model, Notifications notifications) {
        super(model, notifications);

        createView();

        this.onDiagramsChangedListener = this::onDiagramsChangedListener;

        model.getProjects().addListener(this::onProjectsChangedListener);
        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
    }

    private void onProjectsChangedListener(ListChangeListener.Change<? extends ProjectViewModel> projectsChange) {
        while (projectsChange.next()) {
            projectsChange.getAddedSubList().forEach(added -> added.getDiagrams().addListener(onDiagramsChangedListener));
            projectsChange.getAddedSubList().forEach(removed -> removed.getDiagrams().removeListener(onDiagramsChangedListener));
        }
    }

    private void onDiagramsChangedListener(ListChangeListener.Change<? extends DiagramViewModel> diagramsChange) {
        while (diagramsChange.next()) {
            if (diagramsChange.wasAdded()) {
                for (var added : diagramsChange.getAddedSubList()) {
                    createDiagramView(added);
                }
            } else if (diagramsChange.wasRemoved()) {
                for (var removed : diagramsChange.getRemoved()) {
                    var tab = getTabForDiagram(removed);
                    (diagramViews.get(tab)).unbindViewModels();
                    if (tab != null) {
                        diagramViews.remove(tab);
                    }
                }
            }
        }
    }

    private String createTabName(DiagramViewModel diagramViewModel) {
        return String.format("%s/%s", diagramViewModel.getProject().nameProperty().get(), diagramViewModel.nameProperty().get());
    }

    private void createView() {
        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        diagramViews = new HashMap<>();

        tabPane.getSelectionModel().selectedItemProperty().addListener(changeEvent -> {
            var selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab == null) {
                model.selectedDiagramProperty().set(null);
                return;
            }

            var diagram = diagramViews.get(selectedTab).getDiagramViewModel();
            model.selectedDiagramProperty().set(diagram);
        });
    }

    private void createDiagramView(DiagramViewModel diagramViewModel) {
        var tabName = createTabName(diagramViewModel);
        var diagramView = new DiagramView(notifications, model, diagramViewModel);
        var tab = new Tab(tabName, diagramView.getRoot());
        tab.setOnClosed(event -> {
            (diagramViews.get(tab)).unbindViewModels();
            diagramViews.remove(tab);
        });

        diagramViews.put(tab, diagramView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private DiagramView getSelectedDiagramView() {
        var selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            return null;
        }

        return diagramViews.get(selectedTab);
    }

    @Override
    public Node getRoot() {
        return tabPane;
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (newDiagram == null) {
            return;
        }

        if (isOpened(newDiagram)) {
            var tab = getTabForDiagram(newDiagram);
            tabPane.getSelectionModel().select(tab);
        } else {
            createDiagramView(newDiagram);
        }
    }

    private Tab getTabForDiagram(DiagramViewModel diagramViewModel) {
        for (var entry : diagramViews.entrySet()) {
            if (entry.getValue().getDiagramViewModel() == diagramViewModel) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean isOpened(DiagramViewModel diagramViewModel) {
        return getTabForDiagram(diagramViewModel) != null;
    }

}
