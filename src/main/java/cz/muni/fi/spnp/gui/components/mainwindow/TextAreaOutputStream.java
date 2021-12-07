package cz.muni.fi.spnp.gui.components.mainwindow;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Allows the redirection of output streams to the output panel located in the bottom part of the main view.
 * Supports naive buffering functionality.
 */
public class TextAreaOutputStream extends OutputStream {

    private final TextArea textArea;
    private final StringBuilder stringBuilder;

    public TextAreaOutputStream(TextArea textArea) {
        this.textArea = textArea;
        this.stringBuilder = new StringBuilder();
    }

    @Override
    public void write(int b) {
        stringBuilder.append(Character.toString(b));
    }

    @Override
    public void flush() throws IOException {
        var str = stringBuilder.toString();
        Platform.runLater(() -> {
            this.textArea.appendText(str);
        });
        stringBuilder.setLength(0);
    }

}

