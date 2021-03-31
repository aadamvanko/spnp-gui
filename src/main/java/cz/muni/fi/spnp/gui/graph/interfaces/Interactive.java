package cz.muni.fi.spnp.gui.graph.interfaces;

import javafx.scene.input.MouseEvent;

interface Interactive {
    default void onMousePressedHandler(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    default void onMouseDraggedHandler(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }

    default void onMouseReleasedHandler(MouseEvent mouseEvent) {
        mouseEvent.consume();
    }
}
