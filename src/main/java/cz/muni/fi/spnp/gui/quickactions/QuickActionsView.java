package cz.muni.fi.spnp.gui.quickactions;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class QuickActionsView extends Component {

    private final HBox hbox;

    public QuickActionsView() {
        hbox = new HBox();

        Button buttonNewProject = new Button("new project");
        hbox.getChildren().add(buttonNewProject);
    }

    @Override
    public Node getRoot() {
        return hbox;
    }
}
