package cz.muni.fi.spnp.gui.components.projects;

import cz.muni.fi.spnp.gui.components.common.TreeViewContainer;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.menu.diagram.DiagramDetailsView;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectDetailsView;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectSaver;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.stream.Collectors;

/**
 * Projects component located in the left side panel. Shows the projects with the diagrams and support context menus.
 */
public class ProjectsComponent extends TreeViewContainer<DisplayableViewModel> {

    public ProjectsComponent(Model model) {
        super(model, "Projects");

        createView();

        model.getProjects().addListener(this::onProjectsChangedListener);
    }

    private void createView() {
        buttonAdd.setVisible(false);
    }

    @Override
    protected EventHandler<? super MouseEvent> getOnItemMouseClickHandler() {
        return mouseEvent -> {
            var sourceItem = ((TreeCell<DisplayableViewModel>) mouseEvent.getSource()).getItem();
            if (sourceItem instanceof DiagramViewModel && mouseEvent.getClickCount() == 2) {
                model.selectedDiagramProperty().set((DiagramViewModel) sourceItem);
            } else if (sourceItem instanceof ProjectViewModel && mouseEvent.getButton() == MouseButton.SECONDARY && mouseEvent.getClickCount() == 1) {
                var projectTreeItem = (TreeCell<DisplayableViewModel>) mouseEvent.getSource();
                var projectViewModel = (ProjectViewModel) sourceItem;
                var menuItemNewDiagram = new MenuItem("New Diagram");
                menuItemNewDiagram.setOnAction(actionEvent -> new DiagramDetailsView(model, null, ItemViewMode.ADD, projectViewModel).getStage().showAndWait());
                var menuItemRenameProject = new MenuItem("Rename Project");
                menuItemRenameProject.setOnAction(actionEvent -> new ProjectDetailsView(model, projectViewModel, ItemViewMode.EDIT).getStage().showAndWait());
                var menuItemSaveProject = new MenuItem("Save Project");
                menuItemSaveProject.setOnAction(actionEvent -> {
                    var projectSaver = new ProjectSaver(treeView.getScene().getWindow(), projectViewModel);
                    projectSaver.save();
                });
                var menuItemCloseProject = new MenuItem("Close Project");
                menuItemCloseProject.setOnAction(actionEvent -> model.getProjects().remove(projectViewModel));
                var projectContextMenu = new ContextMenu(menuItemNewDiagram, new SeparatorMenuItem(), menuItemRenameProject, menuItemSaveProject, menuItemCloseProject);
                projectContextMenu.show(projectTreeItem.getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else if (sourceItem instanceof DiagramViewModel && mouseEvent.getButton() == MouseButton.SECONDARY && mouseEvent.getClickCount() == 1) {
                var diagramTreeItem = (TreeCell<DisplayableViewModel>) mouseEvent.getSource();
                var diagramViewModel = (DiagramViewModel) sourceItem;
                var menuItemRenameDiagram = new MenuItem("Rename Diagram");
                menuItemRenameDiagram.setOnAction(actionEvent -> new DiagramDetailsView(model, diagramViewModel, ItemViewMode.EDIT, null).getStage().showAndWait());
                var menuItemDeleteDiagram = new MenuItem("Delete Diagram");
                menuItemDeleteDiagram.setOnAction(actionEvent -> diagramViewModel.getProject().getDiagrams().remove(diagramViewModel));
                var diagramContextMenu = new ContextMenu(menuItemRenameDiagram, menuItemDeleteDiagram);
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
