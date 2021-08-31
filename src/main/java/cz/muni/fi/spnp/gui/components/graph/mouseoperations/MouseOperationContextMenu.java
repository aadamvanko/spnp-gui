package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationCopyElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationCutElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationPasteElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationSelectAll;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DragPointViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MouseOperationContextMenu extends MouseOperation {

    private final ContextMenu contextMenu;
    private final Point2D position;
    private final Node contextNode;

    public MouseOperationContextMenu(GraphView graphView, Node contextNode, Point2D position) {
        super(graphView);
        this.contextNode = contextNode;
        this.position = position;

        var selectAllItem = new MenuItem("Select all");
        selectAllItem.setOnAction(this::onSelectAllHandler);
        var pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(this::onPasteHandler);
        pasteItem.setDisable(graphView.getModel().getClipboardElements().isEmpty());
        var copyItem = new MenuItem("Copy");
        copyItem.setOnAction(this::onCopyHandler);
        var cutItem = new MenuItem("Cut");
        cutItem.setOnAction(this::onCutHandler);
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(this::onDeleteHandler);
//        SeparatorMenuItem separator = new SeparatorMenuItem();
//        MenuItem propertiesItem = new MenuItem("Properties");
//        propertiesItem.setOnAction(this::onPropertiesHandler);

        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(selectAllItem, pasteItem, copyItem, cutItem, deleteItem/*, separator, propertiesItem*/);
    }

    private void onSelectAllHandler(ActionEvent actionEvent) {
        new OperationSelectAll(graphView).execute();
    }

    private void onPasteHandler(ActionEvent actionEvent) {
        new OperationPasteElements(graphView).execute();
    }

    private void onCopyHandler(ActionEvent actionEvent) {
        new OperationCopyElements(graphView).execute();
    }

    private void onCutHandler(ActionEvent actionEvent) {
        new OperationCutElements(graphView).execute();
    }

    private void onDeleteHandler(ActionEvent actionEvent) {
        var diagramViewModel = graphView.getDiagramViewModel();

        // clear selection first, because removing from elements causes unbind and null pointer exception
        var selectedCopy = new ArrayList<>(diagramViewModel.getSelected());
        diagramViewModel.resetSelection();

        selectedCopy.forEach(elementViewModel -> {
            if (elementViewModel instanceof ArcViewModel) {
                ((ArcViewModel) elementViewModel).removeFlushFunctionChangeListener();
            }
        });
        diagramViewModel.getElements().removeAll(selectedCopy);
        selectedCopy.stream()
                .filter(elementViewModel -> elementViewModel instanceof DragPointViewModel)
                .forEach(dragPointViewModel -> {
                    var arcs = ViewModelUtils.onlyElements(ArcViewModel.class, diagramViewModel.getElements());
                    arcs.forEach(arcViewModel -> {
                        arcViewModel.getDragPoints().remove(dragPointViewModel);
                        try {
                            arcViewModel.getRemoveStraightLinesCallback().call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                });
        diagramViewModel.removeDisconnectedArcs();
    }

    private void onPropertiesHandler(ActionEvent actionEvent) {
        // TODO select/focus properties component
        System.out.println("missing code handler");
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        var diagramViewModel = graphView.getDiagramViewModel();
        if (diagramViewModel.getSelected().size() <= 1) {
            diagramViewModel.select(List.of(graphElementView.getViewModel()));
        }
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        contextMenu.show(contextNode, position.getX(), position.getY());
    }

    @Override
    public void finish() {
        contextMenu.hide();
    }
}
