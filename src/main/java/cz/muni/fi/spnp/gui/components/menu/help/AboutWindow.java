package cz.muni.fi.spnp.gui.components.menu.help;

import cz.muni.fi.spnp.gui.components.common.UIWindowComponent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class AboutWindow extends UIWindowComponent {

    public AboutWindow() {
        createView();
    }

    private void createView() {
        var text = "This application aims to replace old SPNP GUI tool for creating Petri Nets, converting them to CSPL and running the simulation with SPNP package." + System.lineSeparator();
        text += "More info as well as the source code can be found in the repository." + System.lineSeparator();
        text += System.lineSeparator();
        text += "Repository: https://github.com/aadamvanko/spnp-gui" + System.lineSeparator();
        text += "Author: Adam Vaňko" + System.lineSeparator();
        text += "License: The 3-Clause BSD License";
        var label = new Label(text);

        var buttonOk = new Button("OK");
        buttonOk.setOnAction(actionEvent -> stage.close());
        buttonOk.setAlignment(Pos.CENTER);

        label.setWrapText(true);
        var vBox = new VBox(label, buttonOk);
        vBox.setPadding(new Insets(5));
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        var scene = new Scene(vBox, 400, 175);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        stage.setScene(scene);
        stage.setTitle("About");
        stage.setResizable(false);
    }

}
