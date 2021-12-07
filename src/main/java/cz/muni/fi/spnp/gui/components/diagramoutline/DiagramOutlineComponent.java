package cz.muni.fi.spnp.gui.components.diagramoutline;

import cz.muni.fi.spnp.gui.components.common.TreeViewContainer;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class DiagramOutlineComponent extends TreeViewContainer<DisplayableViewModel> {

    private final ListChangeListener<ElementViewModel> onElementsChangedListener;
    private TreeItem<DisplayableViewModel> treeItemDiagram;

    public DiagramOutlineComponent(Model model) {
        super(model, "Diagram Outline");

        createView();

        this.onElementsChangedListener = this::onElementsChangedListener;

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChanged);
    }

    private void createView() {
        buttonAdd.setVisible(false);
    }

    private void onElementsChangedListener(ListChangeListener.Change<? extends ElementViewModel> elementsChange) {
        while (elementsChange.next()) {
            treeItemDiagram.getChildren().removeIf(treeItem -> elementsChange.getRemoved().contains(treeItem.getValue()));

            elementsChange.getAddedSubList().forEach(this::addItemToTree);
        }
    }

    private void onSelectedDiagramChanged(ObservableValue<? extends DiagramViewModel> observableValue, DiagramViewModel oldDiagram, DiagramViewModel newDiagram) {
        treeItemRoot.getChildren().clear();

        if (oldDiagram != null) {
            oldDiagram.getElements().removeListener(this.onElementsChangedListener);
            treeItemRoot.getChildren().remove(treeItemDiagram);
        }

        if (newDiagram != null) {
            treeItemDiagram = createItem(newDiagram);
            treeItemRoot.getChildren().add(treeItemDiagram);

            newDiagram.getElements().addListener(this.onElementsChangedListener);
            newDiagram.getElements().forEach(this::addItemToTree);
        }
    }

    @Override
    protected void addItemToTree(DisplayableViewModel displayableViewModel) {
        var treeItemElement = createItem(displayableViewModel);
        treeItemDiagram.getChildren().add(treeItemElement);
    }

    @Override
    protected EventHandler<? super MouseEvent> getOnItemMouseClickHandler() {
        return mouseEvent -> {
            var sourceItem = ((TreeCell<DisplayableViewModel>) mouseEvent.getSource()).getItem();
            if (sourceItem instanceof ElementViewModel && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                model.selectedDiagramProperty().get().select(List.of((ElementViewModel) sourceItem));
            }
        };
    }

}
