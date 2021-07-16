package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementView;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationCopyElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationCutElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationPasteElements;
import cz.muni.fi.spnp.gui.components.graph.operations.OperationSelectAll;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class MouseOperationContextMenu extends MouseOperation {

    private final ContextMenu contextMenu;
    private Point2D initialMousePosition;
    private GraphElementView graphElementView;

    public MouseOperationContextMenu(GraphView graphView) {
        super(graphView);

        System.out.println("context menu operation created");

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
        graphView.getSelected().forEach(element -> diagramViewModel.getElements().remove(element.getViewModel()));
        diagramViewModel.removeDisconnectedArcs();
        graphView.resetSelection();
    }

    private void onPropertiesHandler(ActionEvent actionEvent) {
        System.out.println("missing code handler");
    }

    @Override
    public void mousePressedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        this.graphElementView = graphElementView;
        initialMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());

        if (graphView.getSelected().size() <= 1) {
            graphView.select(graphElementView);
        }
    }

    @Override
    public void mouseDraggedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleasedHandler(GraphElementView graphElementView, MouseEvent mouseEvent) {
        contextMenu.show(graphElementView.getContextMenuNode(), initialMousePosition.getX(), initialMousePosition.getY());
    }

    @Override
    public void finish() {
        contextMenu.hide();
    }
}
