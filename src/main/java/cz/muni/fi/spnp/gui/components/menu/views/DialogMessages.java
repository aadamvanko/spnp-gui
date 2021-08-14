package cz.muni.fi.spnp.gui.components.menu.views;

import javafx.scene.control.Alert;

public final class DialogMessages {

    private DialogMessages() {
    }

    public static void showWarning(String message) {
        showWarning(message, "Warning");
    }

    public static void showWarning(String message, String title) {
        showMessage(message, "Warning", Alert.AlertType.WARNING);
    }

    public static void showError(String message) {
        showError(message, "Error");
    }

    public static void showError(String message, String title) {
        showMessage(message, title, Alert.AlertType.ERROR);
    }

    private static void showMessage(String message, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
