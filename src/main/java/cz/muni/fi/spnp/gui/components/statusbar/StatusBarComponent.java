package cz.muni.fi.spnp.gui.components.statusbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.beans.value.ObservableValue;
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

        model.outputContentProperty().addListener(this::onOutputContentChangedListener);
    }

    private void onOutputContentChangedListener(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        textArea.setText("");
        textArea.appendText(newValue);
    }

    @Override
    public Node getRoot() {
        return textArea;
    }
}
