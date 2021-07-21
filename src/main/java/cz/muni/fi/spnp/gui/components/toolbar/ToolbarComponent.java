package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.graph.CursorMode;
import cz.muni.fi.spnp.gui.components.graph.elements.GraphElementType;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ToolbarComponent extends ApplicationComponent {

    private final ToolBar toolBar;
    private final ToggleButton viewButton;
    private final Map<GraphElementType, ToggleButton> createButtons;
    private final ToggleGridButton toggleGridButton;

    private final Slider zoomSlider;
    private final Label labelActualZoom;
    private final ChangeListener<DiagramViewModel> onSelectedDiagramChangedListener;
    private final ChangeListener<? super Number> onZoomLevelChangedListener;
    private final ChangeListener<? super Boolean> onGridSnappingChangedListener;
    private DiagramViewModel diagramViewModel;

    public ToolbarComponent(Model model, Notifications notifications) {
        super(model, notifications);

        this.onSelectedDiagramChangedListener = this::onSelectedDiagramChangedListener;
        this.onZoomLevelChangedListener = this::onZoomLevelChangedListener;
        this.onGridSnappingChangedListener = this::onGridSnappingChangedListener;
        model.cursorModeProperty().addListener(this::onCursorModeChangedListener);
        model.selectedDiagramProperty().addListener(this.onSelectedDiagramChangedListener);

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

        zoomSlider = new Slider(DiagramViewModel.ZOOM_MIN_VALUE, DiagramViewModel.ZOOM_MAX_VALUE, 100);
        zoomSlider.setBlockIncrement(DiagramViewModel.ZOOM_STEP);
        zoomSlider.setMajorTickUnit(DiagramViewModel.ZOOM_STEP);
        zoomSlider.setSnapToTicks(true);
        zoomSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            var newValueInt = newVal.intValue();
            newValueInt = newValueInt / 10 * 10;
            zoomSlider.setValue(newValueInt);
            System.out.println(zoomSlider.getValue());
        });

        var gridZoomDetails = new GridPane();
        labelActualZoom = new Label("Actual: 100%");
        var buttonDefaultZoom = new Button("100%");
        buttonDefaultZoom.setOnMouseClicked(this::onDefaultZoomClicked);
        gridZoomDetails.addRow(0, buttonDefaultZoom, labelActualZoom);
        gridZoomDetails.setHgap(10);
        var zoomVBox = new VBox(zoomSlider, gridZoomDetails);
        zoomVBox.setSpacing(8);

        toggleGridButton = new ToggleGridButton(this::onToggleGridButtonClicked);


        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ZoomOutButton(this::onZoomOutButtonClicked).getRoot());
        toolBar.getItems().add(zoomVBox);
        toolBar.getItems().add(new ZoomInButton(this::onZoomInButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(toggleGridButton.getRoot());

        onCursorModeChangedListener(null, null, model.getCursorMode());
    }

    private void onGridSnappingChangedListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        toggleGridButton.getRoot().setSelected(newValue);
    }

    private void onDefaultZoomClicked(MouseEvent mouseEvent) {
        if (diagramViewModel == null) {
            return;
        }
        diagramViewModel.setZoomLevel(100);
    }

    public void onZoomLevelChangedListener(ObservableValue<? extends Number> observableValue,
                                           Number oldValue, Number newValue) {
        labelActualZoom.setText(String.format("Actual: %s%%", newValue));
    }

    public void onSelectedDiagramChangedListener(ObservableValue<? extends DiagramViewModel> observableValue,
                                                 DiagramViewModel oldDiagramViewModel, DiagramViewModel newDiagramViewModel) {
        unbindDiagramViewModel(oldDiagramViewModel);
        bindDiagramViewModel(newDiagramViewModel);
    }

    private void unbindDiagramViewModel(DiagramViewModel diagramViewModel) {
        if (diagramViewModel == null) {
            return;
        }
        zoomSlider.valueProperty().unbindBidirectional(diagramViewModel.zoomLevelProperty());
        diagramViewModel.zoomLevelProperty().removeListener(this.onZoomLevelChangedListener);
        diagramViewModel.gridSnappingProperty().removeListener(this.onGridSnappingChangedListener);
    }

    private void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        if (diagramViewModel == null) {
            return;
        }
        zoomSlider.valueProperty().bindBidirectional(diagramViewModel.zoomLevelProperty());
        diagramViewModel.zoomLevelProperty().addListener(this.onZoomLevelChangedListener);
        onZoomLevelChangedListener(null, null, diagramViewModel.getZoomLevel());
        diagramViewModel.gridSnappingProperty().addListener(this.onGridSnappingChangedListener);
        onGridSnappingChangedListener(null, null, diagramViewModel.isGridSnapping());
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
        if (diagramViewModel == null) {
            return;
        }
        diagramViewModel.zoomOut();
    }

    private void onZoomInButtonClicked(MouseEvent mouseEvent) {
        if (diagramViewModel == null) {
            return;
        }
        diagramViewModel.zoomIn();
    }

    private void onToggleGridButtonClicked(MouseEvent mouseEvent) {
        diagramViewModel.gridSnappingProperty().set(!diagramViewModel.isGridSnapping());
    }

    @Override
    public Node getRoot() {
        return toolBar;
    }
}
