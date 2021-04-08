package cz.muni.fi.spnp.gui.components.menu.views;

import javafx.scene.control.Alert;

public class DialogMessages {
    public static void showError(String message) {
        showError(message, "Error");
    }

    public static void showError(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
