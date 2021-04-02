package cz.muni.fi.spnp.gui.components.toolbar;

import cz.muni.fi.spnp.gui.components.UIComponent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public abstract class CustomImageToggleButton implements UIComponent {

    protected ToggleButton toggleButton;

    public CustomImageToggleButton(EventHandler<MouseEvent> onClickHandler) {
        toggleButton = new ToggleButton();
        toggleButton.setOnMouseClicked(onClickHandler);
    }

    @Override
    public ToggleButton getRoot() {
        return toggleButton;
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        toggleButton.setToggleGroup(toggleGroup);
    }
}
