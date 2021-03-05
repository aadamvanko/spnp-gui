package cz.muni.fi.spnp.gui.statusbar;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class StatusBarView extends Component {

    private final TextArea textArea;

    public StatusBarView() {
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
