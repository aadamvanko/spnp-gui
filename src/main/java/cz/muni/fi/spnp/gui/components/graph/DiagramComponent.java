package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.canvas.GridBackgroundPane;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiagramComponent extends ApplicationComponent {

    private TabPane tabPane;
    private Map<Tab, DiagramView> diagramViews;
    private final ListChangeListener<? super DiagramViewModel> onDiagramsChangedListener;
    private final ChangeListener<DiagramViewMode> onViewModeChangedListener;

    public DiagramComponent(Model model) {
        super(model);

        createView();

        this.onDiagramsChangedListener = this::onDiagramsChangedListener;
        this.onViewModeChangedListener = this::onViewModeChangedListener;

        model.getProjects().addListener(this::onProjectsChangedListener);
        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
    }

    private void onViewModeChangedListener(ObservableValue<? extends DiagramViewMode> observableValue, DiagramViewMode oldValue, DiagramViewMode newValue) {
        var selectedTab = getSelectedTab();
        if (selectedTab == null) {
            return;
        }

        if (newValue == DiagramViewMode.GRAPH) {
            selectedTab.setContent(getSelectedDiagramView().getGraphView().getRoot());
        } else {
            getSelectedDiagramView().getCodeView().prepare();
            selectedTab.setContent(getSelectedDiagramView().getCodeView().getRoot());
        }
    }

    private void onProjectsChangedListener(ListChangeListener.Change<? extends ProjectViewModel> projectsChange) {
        while (projectsChange.next()) {
            projectsChange.getRemoved().forEach(removedProject -> {
                removedProject.getDiagrams().removeListener(this.onDiagramsChangedListener);
                removedProject.getDiagrams().forEach(this::closeTabForDiagram);
            });
            projectsChange.getAddedSubList().forEach(addedProject -> addedProject.getDiagrams().addListener(this.onDiagramsChangedListener));
        }
    }

    private void onDiagramsChangedListener(ListChangeListener.Change<? extends DiagramViewModel> diagramsChange) {
        while (diagramsChange.next()) {
            diagramsChange.getRemoved().forEach(this::closeTabForDiagram);
            diagramsChange.getAddedSubList().forEach(this::createDiagramView);
        }
    }

    private void closeTabForDiagram(DiagramViewModel diagramViewModel) {
        var tab = getTabForDiagram(diagramViewModel);
        if (tab != null) {
            closeTab(tab);
        }
    }

    private void closeTab(Tab tab) {
        EventHandler<Event> handler = tab.getOnClosed();
        if (null != handler) {
            handler.handle(null);
        }
        tab.getTabPane().getTabs().remove(tab);
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
        var diagramView = new DiagramView(model, diagramViewModel);
        var tab = new Tab("tabName", diagramView.getGraphView().getRoot());
        tab.setOnClosed(event -> {
            (diagramViews.get(tab)).unbindViewModels();
            diagramViews.remove(tab);
            tab.textProperty().unbind();
        });

        tab.textProperty().bind(Bindings.createStringBinding(
                () -> MessageFormat.format("{0}/{1}", diagramViewModel.getProject().getName(), diagramViewModel.getName()),
                diagramViewModel.getProject().nameProperty(),
                diagramViewModel.nameProperty())
        );

        diagramViews.put(tab, diagramView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private Tab getSelectedTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

    private DiagramView getSelectedDiagramView() {
        var selectedTab = getSelectedTab();
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

        if (oldDiagram != null) {
            oldDiagram.viewModeProperty().removeListener(this.onViewModeChangedListener);
        }
        if (newDiagram != null) {
            newDiagram.viewModeProperty().addListener(this.onViewModeChangedListener);
            onViewModeChangedListener(null, null, newDiagram.getViewMode());
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

    public void saveScreenshotCurrentDiagram(File file) {
        if (file == null) {
            return;
        }

        var diagram = model.selectedDiagramProperty().get();
        var selected = new ArrayList(diagram.getSelected());
        diagram.resetSelection();
        saveScreenshotDiagram(getSelectedDiagramView().getGraphView().getGridPane(), file);
        diagram.select(selected);
    }

    private void saveScreenshotDiagram(GridBackgroundPane gridBackgroundPane, File file) {
        var writableImage = gridBackgroundPane.snapshot(null, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        } catch (IOException e) {
            System.err.println("Could not save screenshot to file " + file.getAbsolutePath() + " due to error:");
            e.printStackTrace();
        }
    }

}
