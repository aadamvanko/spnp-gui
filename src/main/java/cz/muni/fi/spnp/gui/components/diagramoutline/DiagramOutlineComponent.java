package cz.muni.fi.spnp.gui.components.diagramoutline;

import cz.muni.fi.spnp.gui.components.TreeViewContainer;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TreeItem;

public class DiagramOutlineComponent extends TreeViewContainer<DisplayableViewModel> {

    private final ListChangeListener<ElementViewModel> onElementsChangedListener;
    private TreeItem<DisplayableViewModel> treeItemDiagram;

    public DiagramOutlineComponent(Model model) {
        super(model, "Diagram outline");

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

}
