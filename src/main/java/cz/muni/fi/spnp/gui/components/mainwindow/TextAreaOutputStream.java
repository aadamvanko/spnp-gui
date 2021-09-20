package cz.muni.fi.spnp.gui.components.mainwindow;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {

    private final TextArea textArea;

    public TextAreaOutputStream(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(final int i) {
        Platform.runLater(() -> {
            this.textArea.appendText(Character.toString(i));
        });
    }

}
