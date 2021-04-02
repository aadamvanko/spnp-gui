package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ToolbarComponent extends ApplicationComponent {

    private final ToolBar toolBar;

    public ToolbarComponent(Model model, Notifications notifications) {
        super(model, notifications);

        toolBar = new ToolBar();

        ToggleGroup mouseOperationsGroup = new ToggleGroup();
        var viewButton = new ViewButton(this::onViewButtonClicked).getRoot();
        viewButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(viewButton);

        toolBar.getItems().add(new Separator(Orientation.VERTICAL));

        var createPlaceButton = new CreatePlaceButton(this::onCreatePlaceButtonClicked).getRoot();
        createPlaceButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(createPlaceButton);

        var createTimedTransitionButton = new CreateTimedTransitionButton(this::onCreateTimedTransitionButtonClicked).getRoot();
        createTimedTransitionButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(createTimedTransitionButton);

        var createImmediateTransitionButton = new CreateImmediateTransitionButton(this::onCreateImmediateTransitionButtonClicked).getRoot();
        createImmediateTransitionButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(createImmediateTransitionButton);

        var createStandardArcButton = new CreateStandardArcButton(this::onCreateStandardArcButtonClicked).getRoot();
        createStandardArcButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(createStandardArcButton);

        var createInhibitorArcButton = new CreateInhibitorArcButton(this::onCreateInhibitorArcButtonClicked).getRoot();
        createInhibitorArcButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(createInhibitorArcButton);

        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ZoomOutButton(this::onZoomOutButtonClicked).getRoot());
        toolBar.getItems().add(new ZoomInButton(this::onZoomInButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ToggleGridButton(this::onToggleGridButtonClicked).getRoot());
    }

    private void toggleIfNeeded(MouseEvent mouseEvent) {
        ToggleButton source = (ToggleButton) mouseEvent.getSource();
        System.out.println(mouseEvent);
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            source.setSelected(false);
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            source.setSelected(true);
        }
    }

    private void onViewButtonClicked(MouseEvent mouseEvent) {
        notifications.setGraphCursorMode(CursorMode.VIEW);
    }

    private void onCreatePlaceButtonClicked(MouseEvent mouseEvent) {
        switchCreateMode(mouseEvent, GraphElementType.PLACE);
    }

    private void onCreateTimedTransitionButtonClicked(MouseEvent mouseEvent) {
        switchCreateMode(mouseEvent, GraphElementType.TIMED_TRANSITION);
    }

    private void onCreateImmediateTransitionButtonClicked(MouseEvent mouseEvent) {
        switchCreateMode(mouseEvent, GraphElementType.IMMEDIATE_TRANSITION);
    }

    private void onCreateStandardArcButtonClicked(MouseEvent mouseEvent) {
        switchCreateMode(mouseEvent, GraphElementType.STANDARD_ARC);
    }

    private void onCreateInhibitorArcButtonClicked(MouseEvent mouseEvent) {
        switchCreateMode(mouseEvent, GraphElementType.INHIBITOR_ARC);
    }

    private void switchCreateMode(MouseEvent mouseEvent, GraphElementType graphElementType) {
        toggleIfNeeded(mouseEvent);
        ToggleButton source = (ToggleButton) mouseEvent.getSource();
        System.out.println("source isSelected " + source.isSelected());
        if (source.isSelected()) {
            notifications.setGraphCursorMode(CursorMode.CREATE_MULTIPLE);
        } else {
            notifications.setGraphCursorMode(CursorMode.CREATE);
        }
        notifications.setCreateElementType(graphElementType);
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
