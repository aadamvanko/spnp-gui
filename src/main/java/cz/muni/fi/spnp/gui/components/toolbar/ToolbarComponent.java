package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;

public class ToolbarComponent extends ApplicationComponent {

    private final ToolBar toolBar;

    public ToolbarComponent(Model model, Notifications notifications) {
        super(model, notifications);

        toolBar = new ToolBar();
        toolBar.getItems().add(new ViewButton(this::onViewButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new CreatePlaceButton(this::onCreatePlaceButtonClicked).getRoot());
        toolBar.getItems().add(new CreateTimedTransitionButton(this::onCreateTimedTransitionButtonClicked).getRoot());
        toolBar.getItems().add(new CreateImmediateTransitionButton(this::onCreateImmediateTransitionButtonClicked).getRoot());
        toolBar.getItems().add(new CreateStandardArcButton(this::onCreateStandardArcButtonClicked).getRoot());
        toolBar.getItems().add(new CreateInhibitorArcButton(this::onCreateInhibitorArcButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ZoomOutButton(this::onZoomOutButtonClicked).getRoot());
        toolBar.getItems().add(new ZoomInButton(this::onZoomInButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ToggleGridButton(this::onToggleGridButtonClicked).getRoot());
    }

    private void onViewButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.VIEW);
    }

    private void onCreatePlaceButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.CREATE);
        notifications.setCreateElementType(GraphElementType.PLACE);
    }

    private void onCreateTimedTransitionButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.CREATE);
        notifications.setCreateElementType(GraphElementType.TIMED_TRANSITION);
    }

    private void onCreateImmediateTransitionButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.CREATE);
        notifications.setCreateElementType(GraphElementType.IMMEDIATE_TRANSITION);
    }

    private void onCreateStandardArcButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.CREATE);
        notifications.setCreateElementType(GraphElementType.STANDARD_ARC);
    }

    private void onCreateInhibitorArcButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.CREATE);
        notifications.setCreateElementType(GraphElementType.INHIBITOR_ARC);
    }

    private void onZoomOutButtonClicked(MouseEvent mouseEvent) {

    }

    private void onZoomInButtonClicked(MouseEvent mouseEvent) {
    }

    private void onToggleGridButtonClicked(MouseEvent mouseEvent) {
        notifications.toggleGridSnapping();
    }

    @Override
    public Node getRoot() {
        return toolBar;
    }
}
