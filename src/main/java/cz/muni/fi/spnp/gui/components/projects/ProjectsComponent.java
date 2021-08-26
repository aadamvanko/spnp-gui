package cz.muni.fi.spnp.gui.components.projects;

import cz.muni.fi.spnp.gui.components.TreeViewContainer;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class ProjectsComponent extends TreeViewContainer<DisplayableViewModel> {

    public ProjectsComponent(Model model) {
        super(model, "Projects");

        createView();

        model.getProjects().addListener(this::onProjectsChangedListener);
    }

    private void createView() {
        buttonAdd.setVisible(false);
        VBox.setVgrow(root, Priority.NEVER);
    }

    @Override
    protected EventHandler<? super MouseEvent> getOnItemMouseClickHandler() {
        return mouseEvent -> {
            var sourceItem = ((TreeCell<DisplayableViewModel>) mouseEvent.getSource()).getItem();
            if (sourceItem instanceof DiagramViewModel && mouseEvent.getClickCount() == 2) {
                model.selectedDiagramProperty().set((DiagramViewModel) sourceItem);
            } else if (sourceItem instanceof ProjectViewModel && mouseEvent.getButton() == MouseButton.SECONDARY && mouseEvent.getClickCount() == 1) {
                var projectContextMenu = new ContextMenu();
                var projectTreeItem = (TreeCell<DisplayableViewModel>) mouseEvent.getSource();
                var menuItemCloseProject = new MenuItem("Close project");
                menuItemCloseProject.setOnAction(actionEvent -> model.getProjects().remove((ProjectViewModel) sourceItem));
                projectContextMenu.getItems().add(menuItemCloseProject);
                projectContextMenu.show(projectTreeItem.getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else if (sourceItem instanceof DiagramViewModel && mouseEvent.getButton() == MouseButton.SECONDARY && mouseEvent.getClickCount() == 1) {
                var diagramContextMenu = new ContextMenu();
                var diagramTreeItem = (TreeCell<DisplayableViewModel>) mouseEvent.getSource();
                var menuItemDeleteDiagram = new MenuItem("Delete diagram");
                // TODO remove reference from viewmodelcopyfactory...
                var diagramViewModel = (DiagramViewModel) sourceItem;
                menuItemDeleteDiagram.setOnAction(actionEvent -> diagramViewModel.getProject().getDiagrams().remove(diagramViewModel));
                diagramContextMenu.getItems().add(menuItemDeleteDiagram);
                diagramContextMenu.show(diagramTreeItem.getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
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
            projectsChange.getRemoved().forEach(removedProject -> treeItemRoot.getChildren().removeIf(treeItemProject -> treeItemProject.getValue() == removedProject));

            projectsChange.getAddedSubList().forEach(addedProject -> treeItemRoot.getChildren().add(createItem(addedProject)));
        }
    }
}
