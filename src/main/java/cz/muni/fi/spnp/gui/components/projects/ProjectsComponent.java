package cz.muni.fi.spnp.gui.components.projects;

import cz.muni.fi.spnp.gui.components.TreeViewContainer;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

import java.util.stream.Collectors;

public class ProjectsComponent extends TreeViewContainer<DisplayableViewModel> {

    public ProjectsComponent(Model model) {
        super(model, "Projects");

        model.getProjects().addListener(this::onProjectsChangedListener);
    }

    @Override
    protected EventHandler<? super MouseEvent> getOnItemMouseClickHandler() {
        return mouseEvent -> {
            var sourceItem = ((TreeCell<DisplayableViewModel>) mouseEvent.getSource()).getItem();
            if (sourceItem instanceof DiagramViewModel && mouseEvent.getClickCount() == 2) {
                model.selectedDiagramProperty().set((DiagramViewModel) sourceItem);
            }
        };
    }

    @Override
    protected TreeItem<DisplayableViewModel> createItem(DisplayableViewModel object) {
        TreeItem<DisplayableViewModel> item = new TreeItem<>(object);
        item.setExpanded(true);
        if (object instanceof ProjectViewModel) {
            var projectObject = (ProjectViewModel) object;
            item.getChildren().addAll(projectObject.getDiagrams().stream().map(this::createItem).collect(Collectors.toList()));
            projectObject.getDiagrams().addListener((ListChangeListener.Change<? extends DisplayableViewModel> change) -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        item.getChildren().addAll(change.getAddedSubList().stream().map(this::createItem).collect(Collectors.toList()));
                    }
                    if (change.wasRemoved()) {
                        item.getChildren().removeIf(treeItem -> change.getRemoved().contains(treeItem.getValue()));
                    }
                }
            });
        }

        return item;
    }

    public void onProjectsChangedListener(ListChangeListener.Change<? extends ProjectViewModel> projectsChange) {
        while (projectsChange.next()) {
            if (projectsChange.wasAdded()) {
                projectsChange.getAddedSubList().forEach(addedProject -> treeItemRoot.getChildren().add(createItem(addedProject)));
            }
            // TODO handle closing/deletion of projects
        }
    }
}
