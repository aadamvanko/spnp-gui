package cz.muni.fi.spnp.gui.components.graph.mouseoperations;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElement;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class MouseOperationContextMenu extends MouseOperation {

    private final ContextMenu contextMenu;
    private Point2D initialMousePosition;
    private GraphElement graphElement;

    public MouseOperationContextMenu(GraphView graphView) {
        super(graphView);

        System.out.println("context menu operation created");


        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(this::onDeleteHandler);
//        SeparatorMenuItem separator = new SeparatorMenuItem();
//        MenuItem propertiesItem = new MenuItem("Properties");
//        propertiesItem.setOnAction(this::onPropertiesHandler);

        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(deleteItem/*, separator, propertiesItem*/);
    }

    private void onDeleteHandler(ActionEvent actionEvent) {
        var diagramViewModel = graphView.getDiagramViewModel();
        graphView.getSelected().forEach(element -> diagramViewModel.removeElement(element.getViewModel()));
        diagramViewModel.removeDisconnectedArcs();
        graphView.resetSelection();
    }

    private void onPropertiesHandler(ActionEvent actionEvent) {
        System.out.println("missing code handler");
    }

    @Override
    public void mousePressedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        this.graphElement = graphElement;
        initialMousePosition = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY());

        if (graphView.getSelected().size() <= 1) {
            graphView.select(graphElement);
        }
    }

    @Override
    public void mouseDraggedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleasedHandler(GraphElement graphElement, MouseEvent mouseEvent) {
        contextMenu.show(graphElement.getContextMenuNode(), initialMousePosition.getX(), initialMousePosition.getY());
    }

    @Override
    public void finish() {
        contextMenu.hide();
    }
}
