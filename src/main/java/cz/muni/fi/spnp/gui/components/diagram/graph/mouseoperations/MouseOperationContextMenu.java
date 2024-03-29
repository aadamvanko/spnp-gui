package cz.muni.fi.spnp.gui.components.diagram.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.operations.*;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * Context menu in the graph view.
 */
public class MouseOperationContextMenu extends MouseOperation {

    private final ContextMenu contextMenu;
    private final Point2D position;
    private final Node contextNode;
    private final CheckMenuItem showTransitionDetailsItem;

    public MouseOperationContextMenu(GraphView graphView, Node contextNode, Point2D position) {
        super(graphView);
        this.contextNode = contextNode;
        this.position = position;

        var selectAllItem = new MenuItem("Select All");
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
        var separator = new SeparatorMenuItem();
        showTransitionDetailsItem = new CheckMenuItem("Show Transition Details");
        showTransitionDetailsItem.setOnAction(this::onShowTransitionDetailsHandler);
        showTransitionDetailsItem.setSelected(graphView.getModel().selectedDiagramProperty().get().isShowTransitionDetails());

        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(selectAllItem, pasteItem, copyItem, cutItem, deleteItem, separator, showTransitionDetailsItem);
    }

    private void onSelectAllHandler(ActionEvent actionEvent) {
        new OperationSelectAll(graphView.getModel(), graphView.getDiagramViewModel()).execute();
    }

    private void onPasteHandler(ActionEvent actionEvent) {
        new OperationPasteElements(graphView.getModel(), graphView.getDiagramViewModel()).execute();
    }

    private void onCopyHandler(ActionEvent actionEvent) {
        new OperationCopyElements(graphView.getModel(), graphView.getDiagramViewModel()).execute();
    }

    private void onCutHandler(ActionEvent actionEvent) {
        new OperationCutElements(graphView.getModel(), graphView.getDiagramViewModel()).execute();
    }

    private void onDeleteHandler(ActionEvent actionEvent) {
        new OperationDeleteElements(graphView.getModel(), graphView.getDiagramViewModel()).execute();
    }

    private void onShowTransitionDetailsHandler(ActionEvent actionEvent) {
        var showTransitionDetailsProperty = graphView.getModel().selectedDiagramProperty().get().showTransitionDetailsProperty();
        showTransitionDetailsProperty.set(!showTransitionDetailsProperty.get());
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
