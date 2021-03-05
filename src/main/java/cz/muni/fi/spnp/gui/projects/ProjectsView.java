package cz.muni.fi.spnp.gui.projects;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ProjectsView extends Component {

    private final TreeView treeView;

    public ProjectsView() {
        treeView = new TreeView();

        TreeItem treeItemProject1 = new TreeItem("Project 1");
        treeItemProject1.getChildren().add(new TreeItem("Diagram 1"));
        treeItemProject1.getChildren().add(new TreeItem("Diagram 2"));
        treeItemProject1.getChildren().add(new TreeItem("Diagram 3"));


        TreeItem treeItemProject2 = new TreeItem("Project 2");
        treeItemProject2.getChildren().add(new TreeItem("Diagram 4"));
        treeItemProject2.getChildren().add(new TreeItem("Diagram 5"));

        TreeItem treeItemRoot = new TreeItem("Projects");
        treeItemRoot.getChildren().add(treeItemProject1);
        treeItemRoot.getChildren().add(treeItemProject2);
        treeView.setRoot(treeItemRoot);
    }

    @Override
    public Node getRoot() {
        return treeView;
    }
}
