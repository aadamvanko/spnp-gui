package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;

public class ToolbarComponent extends ApplicationComponent {

    private final ToolBar toolBar;

    public ToolbarComponent(Model model) {
        super(model);

        toolBar = new ToolBar();
        toolBar.getItems().add(new ViewButton(this::onViewButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new CreatePlaceButton(this::onCreatePlaceButtonClicked).getRoot());
        toolBar.getItems().add(new CreateTimedTransitionButton(this::onCreateTimedTransitionButtonClicked).getRoot());
        toolBar.getItems().add(new CreateImmediateTransitionButton(this::onCreateImmediateTransitionButtonClicked).getRoot());
        toolBar.getItems().add(new CreateStandardArcButton(this::onCreateStandardArcButtonClicked).getRoot());
        toolBar.getItems().add(new CreateInhibitorArcButton(this::onCreateStandardArcButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ZoomOutButton(this::onZoomOutButtonClicked).getRoot());
        toolBar.getItems().add(new ZoomInButton(this::onZoomInButtonClicked).getRoot());
        toolBar.getItems().add(new Separator(Orientation.VERTICAL));
        toolBar.getItems().add(new ToggleGridButton(this::onToggleGridButtonClicked).getRoot());
    }

    private void onViewButtonClicked(MouseEvent mouseEvent) {

    }

    private void onCreatePlaceButtonClicked(MouseEvent mouseEvent) {

    }

    private void onCreateTimedTransitionButtonClicked(MouseEvent mouseEvent) {
    }

    private void onCreateImmediateTransitionButtonClicked(MouseEvent mouseEvent) {
    }

    private void onCreateStandardArcButtonClicked(MouseEvent mouseEvent) {
    }

    private void onZoomOutButtonClicked(MouseEvent mouseEvent) {
    }

    private void onZoomInButtonClicked(MouseEvent mouseEvent) {
    }

    private void onToggleGridButtonClicked(MouseEvent mouseEvent) {
    }

    @Override
    public Node getRoot() {
        return toolBar;
    }
}
