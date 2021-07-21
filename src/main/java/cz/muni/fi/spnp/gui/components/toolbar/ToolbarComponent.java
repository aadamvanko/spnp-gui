package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class ToolbarComponent extends ApplicationComponent {

    private final ToolBar toolBar;
    private final ToggleButton viewButton;
    private final Map<GraphElementType, ToggleButton> createButtons;

    public ToolbarComponent(Model model, Notifications notifications) {
        super(model, notifications);

        model.cursorModeProperty().addListener(this::onCursorModeChangedListener);

        toolBar = new ToolBar();
        createButtons = new HashMap<>();

        ToggleGroup mouseOperationsGroup = new ToggleGroup();
        viewButton = new ViewButton(this::onViewButtonClicked).getRoot();
        viewButton.setToggleGroup(mouseOperationsGroup);
        toolBar.getItems().add(viewButton);

        toolBar.getItems().add(new Separator(Orientation.VERTICAL));

        var createPlaceButton = new CreatePlaceButton(this::onCreatePlaceButtonClicked).getRoot();
        createPlaceButton.setToggleGroup(mouseOperationsGroup);
        createButtons.put(GraphElementType.PLACE, createPlaceButton);
        toolBar.getItems().add(createPlaceButton);

        var createTimedTransitionButton = new CreateTimedTransitionButton(this::onCreateTimedTransitionButtonClicked).getRoot();
        createTimedTransitionButton.setToggleGroup(mouseOperationsGroup);
        createButtons.put(GraphElementType.TIMED_TRANSITION, createTimedTransitionButton);
        toolBar.getItems().add(createTimedTransitionButton);

        var createImmediateTransitionButton = new CreateImmediateTransitionButton(this::onCreateImmediateTransitionButtonClicked).getRoot();
        createImmediateTransitionButton.setToggleGroup(mouseOperationsGroup);
        createButtons.put(GraphElementType.IMMEDIATE_TRANSITION, createImmediateTransitionButton);
        toolBar.getItems().add(createImmediateTransitionButton);

        var createStandardArcButton = new CreateStandardArcButton(this::onCreateStandardArcButtonClicked).getRoot();
        createStandardArcButton.setToggleGroup(mouseOperationsGroup);
        createButtons.put(GraphElementType.STANDARD_ARC, createStandardArcButton);
        toolBar.getItems().add(createStandardArcButton);

        var createInhibitorArcButton = new CreateInhibitorArcButton(this::onCreateInhibitorArcButtonClicked).getRoot();
        createInhibitorArcButton.setToggleGroup(mouseOperationsGroup);
        createButtons.put(GraphElementType.INHIBITOR_ARC, createInhibitorArcButton);
        toolBar.getItems().add(createInhibitorArcButton);

        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ZoomOutButton(this::onZoomOutButtonClicked).getRoot());
        toolBar.getItems().add(new ZoomInButton(this::onZoomInButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ToggleGridButton(this::onToggleGridButtonClicked).getRoot());

        model.cursorModeProperty().set(CursorMode.VIEW);
    }

    private void onCursorModeChangedListener(ObservableValue<? extends CursorMode> observableValue, CursorMode oldCursorMode, CursorMode newCursorMode) {
        if (newCursorMode == CursorMode.VIEW) {
            viewButton.setSelected(true);
            viewButton.requestFocus();
        } else if (newCursorMode == CursorMode.CREATE) {
            createButtons.get(model.getCreateElementType()).setSelected(false);
        } else if (newCursorMode == CursorMode.CREATE_MULTIPLE) {
            createButtons.get(model.getCreateElementType()).setSelected(true);
        }
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
        model.cursorModeProperty().set(CursorMode.VIEW);
        viewButton.setSelected(true);
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
        model.createElementTypeProperty().set(graphElementType);
        if (source.isSelected()) {
            model.cursorModeProperty().set(CursorMode.CREATE_MULTIPLE);
        } else {
            model.cursorModeProperty().set(CursorMode.CREATE);
        }
    }

    private void onZoomOutButtonClicked(MouseEvent mouseEvent) {

    }

    private void onZoomInButtonClicked(MouseEvent mouseEvent) {
    }

    private void onToggleGridButtonClicked(MouseEvent mouseEvent) {
        model.gridSnappingProperty().set(!model.isGridSnapping());
    }

    @Override
    public Node getRoot() {
        return toolBar;
    }
}
