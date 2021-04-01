package cz.muni.fi.spnp.gui.components.functions;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FunctionsCategoriesComponent extends ApplicationComponent {

    private final TreeView treeView;

    public FunctionsCategoriesComponent(Model model, Notifications notifications) {
        super(model, notifications);

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
