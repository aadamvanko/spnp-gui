package cz.muni.fi.spnp.gui.components;

import cz.muni.fi.spnp.gui.components.diagramoutline.TreeItemsIconsLoader;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public abstract class TreeViewContainer<T extends DisplayableViewModel> extends ViewContainer {

    protected final TreeItemsIconsLoader treeItemsIconsLoader;
    protected TreeView<DisplayableViewModel> treeView;
    protected TreeItem<DisplayableViewModel> treeItemRoot;

    public TreeViewContainer(Model model, String title) {
        super(model, title);

        treeItemsIconsLoader = new TreeItemsIconsLoader(16);

        createView();
    }

    protected void addItemToTree(T viewModel) {
        treeItemRoot.getChildren().add(createItem(viewModel));
    }

    protected Callback<TreeView<DisplayableViewModel>, TreeCell<DisplayableViewModel>> getCellFactory() {
        return tv -> {
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

            cell.setOnMouseClicked(getOnItemMouseClickHandler());

            return cell;
        };
    }

    protected EventHandler<? super MouseEvent> getOnItemMouseClickHandler() {
        return null;
    }

    private void createView() {
        treeView = new TreeView<>();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeItemRoot = createItem(new DisplayableViewModel("ROOT"));
        treeView.setRoot(treeItemRoot);
        treeView.setShowRoot(false);
        treeItemRoot.setExpanded(true);

        treeView.setCellFactory(getCellFactory());

        root.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
    }

    protected TreeItem<DisplayableViewModel> createItem(DisplayableViewModel displayableViewModel) {
        TreeItem<DisplayableViewModel> item = new TreeItem<>(displayableViewModel);
        item.setExpanded(true);
        return item;
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
