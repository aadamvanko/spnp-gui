package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.gui.components.common.UIWindowComponent;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Prevents the closing of the window by mistake to prevent the potential loss of the data.
 */
public class ConfirmationWindowExit extends UIWindowComponent {

    private boolean shouldExit;

    public ConfirmationWindowExit() {
        createView();
    }

    private void createView() {
        var labelText = new Label("Do you have all your work saved?");
        labelText.setWrapText(true);

        var buttonExit = new Button("Yes, exit.");
        buttonExit.setOnAction(this::onButtonExitHandler);

        var buttonContinue = new Button("No, continue.");
        buttonContinue.setOnAction(this::onButtonContinueHandler);

        var buttons = new HBox(buttonExit, buttonContinue);
        buttons.setSpacing(5);
        buttons.setAlignment(Pos.CENTER);

        var vBox = new VBox(labelText, buttons);
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(5));
        vBox.setAlignment(Pos.CENTER);

        var scene = new Scene(vBox);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        stage.setScene(scene);
        stage.setTitle("Closing application");
        stage.setWidth(290);
        stage.setHeight(130);
        stage.setResizable(false);
    }

    private void onButtonExitHandler(ActionEvent actionEvent) {
        shouldExit = true;
        stage.close();
    }

    private void onButtonContinueHandler(ActionEvent actionEvent) {
        shouldExit = false;
        stage.close();
    }

    public boolean shouldExit() {
        return shouldExit;
    }

}
