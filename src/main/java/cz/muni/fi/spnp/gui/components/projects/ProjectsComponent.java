package cz.muni.fi.spnp.gui.components.projects;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.NewProjectAddedListener;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ProjectsComponent extends ApplicationComponent implements NewProjectAddedListener {

    private final TreeView treeView;
    private final TreeItem treeItemRoot;

    public ProjectsComponent(Model model, Notifications notifications) {
        super(model, notifications);

        treeView = new TreeView();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeItemRoot = new TreeItem("Projects");
        treeView.setRoot(treeItemRoot);
        treeItemRoot.setExpanded(true);

        notifications.addNewProjectAddedListener(this);
    }

    @Override
    public Node getRoot() {
        return treeView;
    }

    @Override
    public void onNewProjectAdded(ProjectViewModel projectViewModel) {
        var treeItemProject = new TreeItem(projectViewModel.nameProperty().get());
        var orderedDiagrams = projectViewModel.getDiagrams()
                .stream()
                .sorted(Comparator.comparing((diagramViewModel) -> diagramViewModel.nameProperty().get()))
                .collect(Collectors.toList());
        for (var diagram : orderedDiagrams) {
            treeItemProject.getChildren().add(diagram.nameProperty());
        }
        treeItemRoot.getChildren().add(treeItemProject);
        treeItemProject.setExpanded(true);
    }
}
