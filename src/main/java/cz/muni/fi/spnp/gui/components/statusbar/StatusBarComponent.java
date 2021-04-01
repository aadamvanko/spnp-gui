package cz.muni.fi.spnp.gui.components.statusbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class StatusBarComponent extends ApplicationComponent {

    private final TextArea textArea;

    public StatusBarComponent(Model model, Notifications notifications) {
        super(model, notifications);

        textArea = new TextArea();
        textArea.setPrefRowCount(1);
        textArea.setEditable(false);
        textArea.setText("All went good");
    }

    @Override
    public Node getRoot() {
        return textArea;
    }
}
