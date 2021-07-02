package cz.muni.fi.spnp.gui.components.projects;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.diagramoutline.TreeItemsIconsLoader;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.NewDiagramAddedListener;
import cz.muni.fi.spnp.gui.notifications.NewProjectAddedListener;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectsComponent extends ApplicationComponent implements NewProjectAddedListener {

    private TreeView<DisplayableViewModel> treeView;
    private TreeItem<DisplayableViewModel> treeItemRoot;
    private final TreeItemsIconsLoader treeItemsIconsLoader;

    public ProjectsComponent(Model model, Notifications notifications) {
        super(model, notifications);

        treeItemsIconsLoader = new TreeItemsIconsLoader(16);

        createView();

        notifications.addNewProjectAddedListener(this);
    }

    private void createView() {
        treeView = new TreeView<>();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeItemRoot = createItem(new DisplayableViewModel("PROJECTS ROOT"));
        treeView.setRoot(treeItemRoot);
        treeView.setShowRoot(false);
        treeItemRoot.setExpanded(true);

        treeView.setCellFactory(tv -> {
            var cell = new TreeCell<DisplayableViewModel>() {

                @Override
                protected void updateItem(DisplayableViewModel item, boolean empty) {
                    super.updateItem(item, empty);
                    textProperty().unbind();
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        textProperty().bind(item.nameProperty());
                        setGraphic(treeItemsIconsLoader.createIcon(item));
                    }
                }
            };

            cell.setOnMouseClicked(mouseEvent -> {
                var sourceItem = cell.getItem();
                if (sourceItem instanceof DiagramViewModel && mouseEvent.getClickCount() == 2) {
                    model.selectedDiagramProperty().set((DiagramViewModel) sourceItem);
                }
            });

            return cell;
        });
    }

    private TreeItem<DisplayableViewModel> createItem(DisplayableViewModel object) {
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

    @Override
    public Node getRoot() {
        return treeView;
    }

    @Override
    public void onNewProjectAdded(ProjectViewModel projectViewModel) {
        treeItemRoot.getChildren().add(createItem(projectViewModel));
    }
}
