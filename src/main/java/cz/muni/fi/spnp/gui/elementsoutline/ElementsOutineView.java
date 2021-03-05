package cz.muni.fi.spnp.gui.elementsoutline;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ElementsOutineView extends Component {


    private final TreeView treeView;

    public ElementsOutineView() {
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
