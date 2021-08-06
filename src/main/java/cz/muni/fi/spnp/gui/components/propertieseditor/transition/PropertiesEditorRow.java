package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import javafx.scene.Node;

public class PropertiesEditorRow {

    private final Node left;
    private final Node right;

    public PropertiesEditorRow(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

}
