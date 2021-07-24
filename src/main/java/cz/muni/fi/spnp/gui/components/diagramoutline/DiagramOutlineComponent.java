package cz.muni.fi.spnp.gui.components.diagramoutline;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.stream.Collectors;

public class DiagramOutlineComponent extends ApplicationComponent {

    private TreeView<DisplayableViewModel> treeView;
    private TreeItem<DisplayableViewModel> treeItemRoot;
    private ListChangeListener<? super DisplayableViewModel> listChangeListener;
    private final TreeItemsIconsLoader treeItemsIconsLoader;

    public DiagramOutlineComponent(Model model) {
        super(model);

        treeItemsIconsLoader = new TreeItemsIconsLoader(16);

        createView();

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
    }

    private void createView() {
        treeView = new TreeView<>();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeItemRoot = createItem(new DisplayableViewModel("OUTLINE ROOT"));
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

            return cell;
        });
    }

    private TreeItem<DisplayableViewModel> createItem(DisplayableViewModel object) {
        TreeItem<DisplayableViewModel> item = new TreeItem<>(object);
        item.setExpanded(true);
        if (object instanceof DiagramViewModel) {
            var diagramObject = (DiagramViewModel) object;
            item.getChildren().addAll(diagramObject.getElements().stream().map(this::createItem).collect(Collectors.toList()));
            listChangeListener = (ListChangeListener.Change<? extends DisplayableViewModel> change) -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        item.getChildren().addAll(change.getAddedSubList().stream().map(this::createItem).collect(Collectors.toList()));
                    }
                    if (change.wasRemoved()) {
                        item.getChildren().removeIf(treeItem -> change.getRemoved().contains(treeItem.getValue()));
                    }
                }
            };
            diagramObject.getElements().addListener(listChangeListener);
        }

        return item;
    }

    @Override
    public Node getRoot() {
        return treeView;
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        if (!treeItemRoot.getChildren().isEmpty()) {
            DiagramViewModel diagramObject = (DiagramViewModel) treeItemRoot.getChildren().get(0).getValue();
            diagramObject.getElements().removeListener(listChangeListener);
            treeItemRoot.getChildren().clear();
        }

        if (newDiagram != null) {
            treeItemRoot.getChildren().add(createItem(newDiagram));
        }
    }
}
