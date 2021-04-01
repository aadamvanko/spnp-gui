package cz.muni.fi.spnp.gui.components.elementsoutline;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class DiagramOutlineComponent extends ApplicationComponent {


    private final TreeView treeView;

    public DiagramOutlineComponent(Model model, Notifications notifications) {
        super(model, notifications);

        treeView = new TreeView();

        TreeItem treeItemDiagram1Elements = new TreeItem("Diagram 1");
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Node A"));
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Node B"));
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Transition 1"));
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Transition 2"));

        TreeItem treeItemRoot = new TreeItem("Diagram graph elements");
        treeItemRoot.getChildren().add(treeItemDiagram1Elements);
        treeView.setRoot(treeItemRoot);

    }

    @Override
    public Node getRoot() {
        return treeView;
    }
}
