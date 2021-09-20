package cz.muni.fi.spnp.gui.components.statusbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class StatusBarComponent extends ApplicationComponent {

    private final TextArea textArea;

    public StatusBarComponent(Model model) {
        super(model);

        textArea = new TextArea();
        textArea.setEditable(false);

        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public Node getRoot() {
        return textArea;
    }
}
