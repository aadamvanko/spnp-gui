package cz.muni.fi.spnp.gui.functions;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FunctionsCategoriesView extends Component {

    private final TreeView treeView;

    public FunctionsCategoriesView() {
        treeView = new TreeView();

        TreeItem treeItemCategoryGeneral = new TreeItem("General");
        treeItemCategoryGeneral.getChildren().add(new TreeItem("general_function_1"));
        treeItemCategoryGeneral.getChildren().add(new TreeItem("general_function_2"));

        TreeItem treeItemCategoryGuard = new TreeItem("Guard");
        treeItemCategoryGuard.getChildren().add(new TreeItem("guard_function_1"));
        treeItemCategoryGuard.getChildren().add(new TreeItem("guard_function_2"));

        TreeItem treeItemRoot = new TreeItem("Functions");
        treeItemRoot.getChildren().add(treeItemCategoryGeneral);
        treeItemRoot.getChildren().add(treeItemCategoryGuard);
        treeView.setRoot(treeItemRoot);
    }

    @Override
    public Node getRoot() {
        return treeView;
    }
}
